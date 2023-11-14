package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.suppliers.SupplierPaginatedDTO;
import com.atuservicio.atuservicio.dtos.users.UserPaginatedDTO;
import com.atuservicio.atuservicio.enums.Role;
import com.atuservicio.atuservicio.services.interfaces.ISupplierService;
import com.atuservicio.atuservicio.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard")
public class AdminDashboardController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ISupplierService supplierService;

    @GetMapping("/")
    public String adminDashboard(ModelMap model) {
        return this.clientsPaginated(1, true, model);
    }
    @GetMapping("/clients/{pageNumber}")
    public String clientsPaginated(@PathVariable int pageNumber, @RequestParam Boolean active, ModelMap model) {
        int pageSize = 5;
        UserPaginatedDTO clients = this.userService.
                findPaginated(pageNumber, pageSize, Role.CLIENT, active);
        Role[] roles = Role.values();

        model.addAttribute("roles", roles);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", clients.getTotalPages());
        model.addAttribute("totalItems", clients.getTotalElements());
        model.addAttribute("clients", clients.getClients());
        if (active) {
            return "admin_dashboard/clients_dashboard";
        } else {
            return "admin_dashboard/clients_dashboard_inactive";
        }
    }
    @GetMapping("/suppliers")
    public String suppliersMainDashboard(ModelMap model) {
        return this.supplierPageDashboard(1, model);
    }

    @GetMapping("/suppliers/{pageNumber}")
    public String supplierPageDashboard(@PathVariable int pageNumber, ModelMap model) {
        int pageSize = 5;
        SupplierPaginatedDTO suppliers = this.supplierService.findPaginated(pageNumber, pageSize);

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", suppliers.getTotalPages());
        model.addAttribute("totalItems", suppliers.getTotalElements());
        model.addAttribute("suppliers", suppliers.getSuppliers());
        return "admin_dashboard/suppliers_dashboard";
    }
}
