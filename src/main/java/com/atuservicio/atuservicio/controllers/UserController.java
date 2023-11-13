/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atuservicio.atuservicio.controllers;



import com.atuservicio.atuservicio.dtos.LoginPassDTO;
import com.atuservicio.atuservicio.dtos.users.EditUserDTO;
import com.atuservicio.atuservicio.dtos.users.SaveUserDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserRegisterErrorDTO;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.UserService;

import java.util.ArrayList;
import java.util.List;

import com.atuservicio.atuservicio.services.interfaces.IUserService;
import com.atuservicio.atuservicio.utils.PasswordValidator;
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
    IUserService userService;
    @Autowired
    private PasswordValidator passwordValidator;

    @GetMapping("/register")
    public String register() {

        return "register_client.html";
    }

    @PostMapping("/register")
    public String register(@RequestParam(required = false) String name, @RequestParam(required = false) String email,
            @RequestParam(required = false) String password, @RequestParam(required = false) String password2, ModelMap model) throws MyException {
        
          
            try {
                List<UserRegisterErrorDTO> errors = validar(name, email, password,
                        password2);
                
                if (!errors.isEmpty()) {
                    model.addAttribute("errors", errors);
                    model.put("name", name);
                    model.put("email", email);
                    
                    return "register_client.html";
                }

                SaveUserDTO user = new SaveUserDTO(name, email, password, password2);

                UserInfoDTO u = this.userService.save(user);
                model.put("exito", "usuario registrado correctamente");
                return "redirect:/login";

            } catch (MyException ex) {
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
    public String profile(ModelMap model) throws MyException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserInfoDTO user = userService.getSearchEmailUser(auth.getName());

        model.addAttribute("user", user);

        return "user_profile.html";

    }

    @PostMapping("/edit/{userId}")
    public String editUser(@PathVariable String userId, @RequestParam String name, @RequestParam (required = false) MultipartFile image,
                           @RequestParam String address, @RequestParam Long address_number, @RequestParam String postal_code,
                           @RequestParam String city, @RequestParam String province, @RequestParam String country, ModelMap model) throws MyException {

       UserInfoDTO user = this.userService.edit(new EditUserDTO(userId, name, image, address, address_number, country, province, city, postal_code));

       model.put("updated", "Usuario actualizado");
       model.addAttribute("user", user);
        return "user_profile.html";
    }


    private List<UserRegisterErrorDTO> validar(String name,String email,
                        String password, String password2) throws MyException{
        List<UserRegisterErrorDTO> errors = new ArrayList<>();
        
        if (name.isEmpty() || name == null) {

            errors.add(new UserRegisterErrorDTO("name", "Nombre requerido"));
        }
        if (email.isEmpty() || email == null) {
            
            errors.add(new UserRegisterErrorDTO("email", "Email requerido"));
        } else {
            try {
                UserInfoDTO user = userService.getSearchEmailUser(email);
                errors.add(new UserRegisterErrorDTO("email", "El usuario ya está registardo"));
            } catch (MyException ex) {

            }
            // TODO codigo innecesario para validar email, rompe el registro y sin esta parte funciona y valida correctamente el email
        }
       
        if (password.isEmpty() || password == null) {
            errors.add(new UserRegisterErrorDTO("password", "La contraseña no puede estar vacía"));
        } else {
            if (!this.passwordValidator.isValid(password)) {
                errors.add(new UserRegisterErrorDTO("password", "La contraseña debe contener numeros del 0 al 9, mayusculas y minusculas, y tener entre 5 y 15 caracteres"));
            }
        }
        
        if (!password.equals(password2)) {
            errors.add(new UserRegisterErrorDTO("password2", "Las contraseñas ingresadas deben ser iguales"));
        }
         return errors;

    }
}
