/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atuservicio.atuservicio.controllers;



import com.atuservicio.atuservicio.dtos.EditUserDTO;
import com.atuservicio.atuservicio.dtos.SaveUserDTO;
import com.atuservicio.atuservicio.dtos.UserInfoDTO;
import com.atuservicio.atuservicio.entities.Role;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String registro(@RequestParam String name, @RequestParam String email, 
            @RequestParam String password, String password2, String role, MultipartFile image,
            @RequestParam String address,@RequestParam Long address_number,@RequestParam String postal_code,
            @RequestParam String city,@RequestParam String province, @RequestParam String country, ModelMap modelo) throws MyException {
            
            
            
        try {
            SaveUserDTO user = new SaveUserDTO(image, password, password2, role);
            user.setName(name);
            user.setEmail(email);
            user.setImage(image);
            user.setAddress(address);
            user.setAddress_number(address_number);
            user.setCity(city);
            user.setCountry(country);
            user.setProvince(province);
            user.setPostal_code(postal_code);
            userService.save(user);
            modelo.put("exito", "usuario registrado correctamente");
            return "redirect:/login";
        
        } catch (MyException ex){
           modelo.put("error", ex.getMessage());
           modelo.put("name", name);
           modelo.put("email", email);
           return "register.html";  
        }            

    }

 
    
    @GetMapping("/modify/{id}")
    public String getModify(@PathVariable("id") String id, ModelMap model) {

        // model.addAttribute("user", userService.findById(id)); // esto deberia retornar un userInfoDTO creo xD.

        return "user_modify.html";

    }

    @PostMapping("/modify/{id}")
    public String postModify(@PathVariable("id") String id, String name,String email,MultipartFile image,String address, Long address_number, String city, String province,String country,String postal_code,ModelMap model) {

        try {
            /*
            EDITO JOEL
            El metodo edit ya verifica que el usuario con el id exista
            UserInfoDTO userInfoDTO = userService.getById(id);
            userInfoDTO.setName(name);
            userInfoDTO.setEmail(email);
            userInfoDTO.setImage(image);
            userInfoDTO.setAddress(address);
            userInfoDTO.setAddress_number(address_number);
            userInfoDTO.setCity(city);
            userInfoDTO.setProvince(province);
            userInfoDTO.setCountry(country);
            userInfoDTO.setPostal_code(postal_code);
            userService.edit(userInfoDTO);
            */
            EditUserDTO editUser = new EditUserDTO(name, email, image, address, address_number, city, province, country, postal_code, id);
            userService.edit(editUser);

            model.put("exito", "Se actualizó el usuario correctamente");

        } catch (MyException ex) { //error de compilacion de la exception porque la funcion modifyUser deberia lanzar la MyException(con eso deberia arreglarse).

            model.put("error", ex.getMessage());

            return "user_modify.html";
        }

        return "index.html";
    }

}
