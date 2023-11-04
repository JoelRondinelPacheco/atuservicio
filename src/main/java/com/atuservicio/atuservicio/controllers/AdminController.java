package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.SupplierService;
import com.atuservicio.atuservicio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/clients")
    public String clientsDashboard(ModelMap model) {
        List<UserInfoDTO> clients = this.userService.getAllUsers();
        model.addAttribute("clients", clients);
        return "admin_dashboard";
    }

    @GetMapping("/clients/delete")
    public String deleteUser(@RequestParam String clientId, ModelMap model) {
        try {
            this.userService.delete(clientId);
            List<UserInfoDTO> clients = this.userService.getAllUsers();
            model.addAttribute("clients", clients);
            return "clients_dashboard";
        } catch (MyException ex) {
            return "clients_dashboard";
        }
    }

    @GetMapping("/clients/activate")
    public String activateUser(@RequestParam String clientId, ModelMap model) {
        try {
            this.userService.activate(clientId);
            List<UserInfoDTO> clients = this.userService.getAllUsers();
            model.addAttribute("clients", clients);
            return "clients_dashboard";
        } catch (MyException ex) {
            return "clients_dashboard";
        }
    }

    @GetMapping("/suppliers")
    public String supplierDashboard(ModelMap model) {
        List<SupplierInfoDTO> suppliers = this.supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "suppliers_dashboard";
    }

    @GetMapping("/suppliers/delete")
    public String deleteSupplier(@RequestParam String supplierId, ModelMap model) {
        try {
            this.supplierService.delete(supplierId);
            List<SupplierInfoDTO> suppliers = this.supplierService.getAllSuppliers();
            model.addAttribute("suppliers", suppliers);
            return "suppliers_dashboard";
        } catch (MyException ex) {
            return "suppliers_dashboard";
        }
    }

    @GetMapping("/suppliers/activate")
    public String activateSupplier(@RequestParam String supplierId, ModelMap model) {
        try {
            this.supplierService.activate(supplierId);
            List<SupplierInfoDTO> suppliers = this.supplierService.getAllSuppliers();
            model.addAttribute("suppliers", suppliers);
            return "suppliers_dashboard";
        } catch (MyException ex) {
            return "suppliers_dashboard";
        }
    }


}
