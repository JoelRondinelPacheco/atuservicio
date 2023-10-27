/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
    UserService userService;
    
    @GetMapping("/register")
    public String register(){

        return "register.html";
    }
    
    @PostMapping("/register")
    public String registro(@RequestParam String name, @RequestParam String email, 
            @RequestParam String password, String password2,@RequestParam String rol,
            @RequestParam String calle,@RequestParam long numero,@RequestParam String codPostal,
            @RequestParam String ciudad,@RequestParam String pais, ModelMap modelo) throws MyException {
        
        try {
            userService.registrar(name, email, password, password2, rol, calle, numero, codPostal, ciudad, pais);
            modelo.put("exito", "usuario registrado correctamente");
            return "redirect:/login";
        
        } catch (MyException ex){
            
            modelo.put("error", ex.getMessage());
            modelo.put("name", name);
            modelo.put("email", email);
            return "register.html";
        }
    }
    
    
}
