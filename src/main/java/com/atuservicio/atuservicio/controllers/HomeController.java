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
import com.atuservicio.atuservicio.entities.User;
import com.atuservicio.atuservicio.enums.Role;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.CategoryService;
import com.atuservicio.atuservicio.services.CustomUserDetailsService;
import com.atuservicio.atuservicio.services.ImageService;
import com.atuservicio.atuservicio.services.SupplierService;
import com.atuservicio.atuservicio.services.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ImageService imageService;

    @GetMapping("/")
    public String index(ModelMap model) {

        List<UserInfoDTO> users = this.userService.getAllUsers();

        model.addAttribute("user", users);

        return "index.html";
    }

    @GetMapping("/search")
    public String search() {

        return "search.html";
    }

    @PostMapping("/search")
    public String resultSearch(@RequestParam(required = false) String country,
            @RequestParam(required = false) String province, @RequestParam(required = false) String city,
            @RequestParam(required = false) String email, ModelMap model) {

        if (email.isEmpty()) {
            System.out.println(province);
            System.out.println(country);
            System.out.println(city);
            UserSearchDTO userSearch = new UserSearchDTO(city, province, country);

            List<UserInfoDTO> users = userService.getSearchUsers(userSearch);

            model.addAttribute("locationFound", true);
            model.addAttribute("users", users);

            return "search.html";
        } else {
            try {
                UserInfoDTO user = userService.getSearchEmailUser(email);
                List<UserInfoDTO> users = new ArrayList();

                users.add(user);

                model.addAttribute("locationFound", true);
                model.addAttribute("users", users);

                return "search.html";

            } catch (MyException ex) {

                model.put("error", ex.getMessage());

                return "search.html";
            }
        }
    }

    @GetMapping("/searchByCategory/{categoryId}")
    public String searchBycategory(@PathVariable("categoryId") String categoryId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size, ModelMap model, Principal principal) {

        try {
            Page<SupplierInfoDTO> users;
            String province = "";
            if (principal == null) {
                System.out.println("Usuario no logueado");

                users = supplierService.getPageByCategoryAndProvince(categoryId, province, page, size);

            } else {
                System.out.println("Usuario logueado");

                province = userService.getSearchEmailUser(principal.getName()).getProvince();

                users = supplierService.getPageByCategoryAndProvince(categoryId, province, page, size);

            }
            // List<SupplierInfoDTO> users =
            // supplierService.getSuppliersByCategory(categoryId);
            model.addAttribute("locationFound", true);
            model.addAttribute("users", users);

            return "services.html";

        } catch (MyException ex) {

            model.put("error", ex.getMessage());

            return "services.html";
        }

    }

    @GetMapping("/profile")
    public String profile(ModelMap model) throws MyException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        System.out.println(email);
        String role = auth.getAuthorities().toString();

        if (role.equals("[ROLE_SUPPLIER]")) {
            SupplierInfoDTO supplier = this.supplierService.getByEmail(email);
            model.addAttribute("user", supplier);
            return "supplier_profile";
        } else if (role.equals("[ROLE_CLIENT]") || role.equals("[ROLE_MODERATOR]") || role.equals("[ROLE_ADMIN]")) {
            UserInfoDTO user = this.userService.getSearchEmailUser(email);
            model.addAttribute("user", user);
            return "user_profile";

        } else {
            return "index.html";
        }
    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam String id, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();

        if (role.equals("[ROLE_SUPPLIER]")) {
            try {
                SupplierInfoDTO supplier = this.supplierService.getById(id);
                List<CategoryInfoDTO> categories = this.categoryService.listAll();
                model.addAttribute("categories", categories);
                model.addAttribute("user", supplier);
                return "supplier_panel";
            } catch (MyException ex) {
                return "index.html";
            }
        } else if (role.equals("[ROLE_CLIENT]") || role.equals("[ROLE_MODERATOR]") || role.equals("[ROLE_ADMIN]")) {
            try {
                UserInfoDTO user = this.userService.getById(id);
                model.addAttribute("user", user);
                return "client_panel";
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        } else {
            return "index.html";
        }
    }

    @GetMapping("/contact")
    public String contact() {

        return "contact.html";
    }

}
