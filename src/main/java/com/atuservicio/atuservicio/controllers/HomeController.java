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


import com.atuservicio.atuservicio.dtos.UserInfoDTO;
import com.atuservicio.atuservicio.dtos.UserSearchDTO;
import com.atuservicio.atuservicio.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @GetMapping("/")
    public String index(){

        return "index.html";
    }

    @GetMapping("/login")
    public String login(){

        return "login.html";
    }
    
    
    
    @GetMapping("/search")
    public String search(){

        return "search.html";
    }
    
    @PostMapping("/search")
    public String resultSearch(@RequestParam(required = false) String country, @RequestParam(required = false) String province,@RequestParam(required = false) String city, ModelMap model){
        
        UserSearchDTO userSearch = new UserSearchDTO(city, province, country);
        
        List <UserInfoDTO> users = userService.getSearchUsers(userSearch);
        
        model.addAttribute("ubicacionEncontrada", true);
        model.addAttribute("users",users);
        
        return "search.html";
    }
}
