/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atuservicio.atuservicio.controllers;

/**
 *
 * @author dario
 */


import com.atuservicio.atuservicio.dtos.LoginPassDTO;
import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserSearchDTO;
import com.atuservicio.atuservicio.entities.Category;
import com.atuservicio.atuservicio.enums.Role;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.CategoryService;
import com.atuservicio.atuservicio.services.SupplierService;
import com.atuservicio.atuservicio.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/")
    public String index(){

        return "index.html";
    }
     
    @GetMapping("/search")
    public String search(){

        return "search.html";
    }
    
    @PostMapping("/search")
    public String resultSearch(@RequestParam(required = false) String country, @RequestParam(required = false) String province,@RequestParam(required = false) String city,@RequestParam(required = false) String email, ModelMap model){

        if(email.isEmpty()){
            UserSearchDTO userSearch = new UserSearchDTO(city, province, country);

            List <UserInfoDTO> users = userService.getSearchUsers(userSearch);

            model.addAttribute("locationFound", true);
            model.addAttribute("users",users);
        
            return "search.html";
        } else{
            String password="";
            LoginPassDTO userSearch = new LoginPassDTO(email, password);
            try {
                UserInfoDTO user = userService.getSearchEmailUser(userSearch);
           
                model.addAttribute("userFound", true);
                model.addAttribute("user",user); 
                
                return "search.html";
                
            } catch (MyException ex){
             
                model.put("error", ex.getMessage());
           
                return "search.html";
            }            
        }
    }

    @GetMapping("/profile")
    public String profile(ModelMap model) throws MyException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String role = auth.getAuthorities().toString();
        System.out.println("admin: " + role.equals("[ROLE_ADMIN]"));

        if (role.equals("[ROLE_SUPPLIER]")) {
            SupplierInfoDTO supplier = this.supplierService.getByEmail(email);
            List<CategoryInfoDTO> categories = this.categoryService.listAll();
            model.addAttribute("categories", categories);
            model.addAttribute("user", supplier);
            return "supplier_profile";
        } else if (role.equals("[ROLE_CLIENT]") || role.equals("[ROLE_MODERATOR]") || role.equals("[ROLE_ADMIN]")) {
            UserInfoDTO user = this.userService.getSearchEmailUser(new LoginPassDTO(email, ""));
            model.addAttribute("user", user);
            return "client_panel";

        } else {
            return "index.html";
        }
    }
}