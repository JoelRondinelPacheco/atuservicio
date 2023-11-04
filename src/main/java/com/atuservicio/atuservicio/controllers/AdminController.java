package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.services.SupplierService;
import com.atuservicio.atuservicio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/dashboard")
    public String adminDashboard(ModelMap model) {
        List<UserInfoDTO> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin_dashboard";
    }


}
