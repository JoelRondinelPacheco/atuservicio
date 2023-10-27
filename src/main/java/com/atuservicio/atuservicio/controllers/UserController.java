/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.UserInfoDTO;
import com.atuservicio.atuservicio.entities.Role;
import com.atuservicio.atuservicio.exceptions.MyException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dario
 */
@Controller
@RequestMapping("/client")
public class UserController {

    @Autowired
    // UserService userService;

    @GetMapping("/register")
    public String register() {

        return "register.html";
    }

    @PostMapping("/register")
    public String registro(@RequestParam String name, @RequestParam String email,
            @RequestParam String password, String password2, @RequestParam String rol,
            @RequestParam String calle, @RequestParam long numero, @RequestParam String codPostal,
            @RequestParam String ciudad, @RequestParam String pais, ModelMap modelo) throws MyException {

        // try {
        // userService.registrar(name, email, password, password2, rol, calle, numero,
        // codPostal, ciudad, pais);
        modelo.put("exito", "usuario registrado correctamente");
        return "redirect:/login";

        // } catch (MyException ex){
        //
        // modelo.put("error", ex.getMessage());
        // modelo.put("name", name);
        // modelo.put("email", email);
        // return "register.html";
        // }
    }

    @GetMapping("/list")
    public String list(ModelMap model) {

        // List<UserInfoDTO> users = userService.listUsers(); //esto deberia retornar una lista userInfoDTO.

        // model.addAttribute("users", users);

        return "user_list.html";

    }

    @GetMapping("/modify/{id}")
    public String getModify(@PathVariable("id") String id, ModelMap model) {

        // model.addAttribute("user", userService.findById(id)); // esto deberia retornar un userInfoDTO creo xD.

        return "user_modify.html";

    }

    @PostMapping("/modify/{id}")
    public String postModify(@PathVariable("id") String id, String name,String email,String image,String addres, Long address_number, String city, String province,String country,String postal_code,ModelMap model) {

        try {
            
            UserInfoDTO userInfoDTO = null; // cambiar null por: userService.findById(id); 
            userInfoDTO.setName(name);
            userInfoDTO.setEmail(email);
            userInfoDTO.setImage(image);
            userInfoDTO.setAddress(addres);
            userInfoDTO.setAddress_number(address_number);
            userInfoDTO.setCity(city);
            userInfoDTO.setProvince(province);
            userInfoDTO.setCountry(country);
            userInfoDTO.setPostal_code(postal_code);
            // userService.modifyUser(userInfoDTO);
            model.put("exito", "Se actualizó el usuario correctamente");

        } catch (MyException ex) { //error de compilacion de la exception porque la funcion modifyUser deberia lanzar la MyException(con eso deberia arreglarse).

            model.put("error", ex.getMessage());

            return "user_modify.html";
        }

        return "index.html";
    }

}
