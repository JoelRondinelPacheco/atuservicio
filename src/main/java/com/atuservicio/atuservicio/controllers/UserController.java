/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atuservicio.atuservicio.controllers;



import com.atuservicio.atuservicio.dtos.users.EditUserDTO;
import com.atuservicio.atuservicio.dtos.users.SaveUserDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.UserService;

import java.util.List;
import org.hibernate.hql.internal.ast.tree.IsNullLogicOperatorNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author dario
 */
@Controller
@RequestMapping("/client")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String register() {

        return "register_client.html";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email, 
            @RequestParam String password, @RequestParam String password2, @RequestParam(required = false) MultipartFile image,
            @RequestParam String address,@RequestParam Long address_number,@RequestParam String postal_code,
            @RequestParam String city,@RequestParam String province, @RequestParam String country, ModelMap model) throws MyException {

        try {

            SaveUserDTO user = new SaveUserDTO(name, email, address, address_number, city,
                    province, country, postal_code, password, password2, image);

            UserInfoDTO u = this.userService.save(user);
            model.put("exito", "usuario registrado correctamente");
            return "redirect:/login";

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            model.put("name", name);
            model.put("email", email);
            return "register_client.html";
        }

    }


    @GetMapping("/modify/{id}")
    public String getModify(@PathVariable("id") String id, ModelMap model) throws MyException {

        UserInfoDTO user = userService.getById(id);

        model.addAttribute("user", user);

        return "user_modify.html";

    }

    @PostMapping("/modify/{id}")
    public String postModify(@PathVariable("id") String id, String name, String email, MultipartFile image, String address, Long address_number, String city, String province, String country, String postal_code, ModelMap model) {

        try {

            EditUserDTO userInfoDTO = new EditUserDTO(id, name, image, address, address_number, country, province, city, postal_code);

            userService.edit(userInfoDTO);
            model.put("exito", "Se actualizó el usuario correctamente");

        } catch (MyException ex) {

            model.put("error", ex.getMessage());

            return "user_modify.html";
        }

        return "index.html";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, ModelMap model) {

        try {

            userService.delete(id);

            model.put("exito", "Se eliminó el usuario correctamente");

        } catch (MyException ex) {

            model.put("error", ex.getMessage());

            return "user_modify.html";
        }

        return "users_list.html";
    }


    @GetMapping("/list")
    public String list(ModelMap model) {

        List<UserInfoDTO> users = userService.getAllUsers();

        model.addAttribute("users", users);

        return "user_list.html";

    }

    @GetMapping("/profile")
    public String profile(@PathVariable("id") String id, ModelMap model) throws MyException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserInfoDTO user = userService.getById(id);

        model.addAttribute("user", user);

        return "user_detail.html";

    }

    @PostMapping("/edit/{userId}")
    public String editUser(@PathVariable String userId, @RequestParam String name, @RequestParam (required = false) MultipartFile image,
                           @RequestParam String address, @RequestParam Long address_number, @RequestParam String postal_code,
                           @RequestParam String city, @RequestParam String province, @RequestParam String country, ModelMap model) throws MyException {

       UserInfoDTO user = this.userService.edit(new EditUserDTO(userId, name, image, address, address_number, country, province, city, postal_code));

       model.put("updated", "Usuario actualizado");
       model.addAttribute("user", user);
        return "client_panel.html";
    }
}
