package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.dtos.categories.EditCategoryDTO;
import com.atuservicio.atuservicio.dtos.categories.SaveCategoryDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserPaginatedDTO;
import com.atuservicio.atuservicio.dtos.users.UserSearchAdminDTO;
import com.atuservicio.atuservicio.dtos.users.UserSearchDTO;
import com.atuservicio.atuservicio.enums.Role;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.SupplierService;
import com.atuservicio.atuservicio.services.UserService;
import com.atuservicio.atuservicio.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/dashboard")
    public String adminDashboard(ModelMap model) {
        return this.clientsPaginated(1, model);
    }

    /*
     * @GetMapping("/dashboard")
     * public String adminDashboard(ModelMap model) {
     * return this.clientsPaginated(1, "name", "asc", model);
     * }
     */
    @PostMapping("/clients/search")
    public String clientSearch(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Role role,
            ModelMap model) throws MyException {

        UserSearchDTO userSearch = new UserSearchDTO(city, province, country, email, role);

        List<UserInfoDTO> clients = userService.findUsers(userSearch);
        model.addAttribute("clients", clients);
        Role[] roles = Role.values();
        model.addAttribute("roles", roles);

        return "clients_dashboard";

    }

    @GetMapping("/clients/{pageNumber}")
    public String clientsPaginated(@PathVariable int pageNumber, ModelMap model) {
        int pageSize = 5;
        UserPaginatedDTO clients = this.userService.findPaginated(pageNumber, pageSize);
        Role[] roles = Role.values();

        model.addAttribute("roles", roles);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", clients.getTotalPages());
        model.addAttribute("totalItems", clients.getTotalElements());
        model.addAttribute("clients", clients.getClients());
        return "clients_dashboard";
    }
    // TODO Agergar page size desde la vista
    // /clients/pageNumber?sortField=name&sortDir=asc
    /*
     * @GetMapping("/clients/{pageNumber}")
     * public String clientsPaginated(@PathVariable int pageNumber, @RequestParam
     * String sortField, @RequestParam String sortDir, ModelMap model) {
     * int pageSize = 5;
     * UserPaginatedDTO clients = this.userService.findPaginated(pageNumber,
     * pageSize, sortField, sortDir);
     * 
     * model.addAttribute("currentPage", pageNumber);
     * model.addAttribute("totalPages", clients.getTotalPages());
     * model.addAttribute("totalItems", clients.getTotalElements());
     * 
     * model.addAttribute("sortField", sortField);
     * model.addAttribute("sortDir", sortDir);
     * model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
     * 
     * model.addAttribute("clients", clients.getClients());
     * return "clients_dashboard";
     * }
     */

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
    public String activateClient(@RequestParam String clientId, ModelMap model) {
        try {
            this.userService.activate(clientId);
            List<UserInfoDTO> clients = this.userService.getAllUsers();
            model.addAttribute("clients", clients);
            return "clients_dashboard";
        } catch (MyException ex) {
            return "clients_dashboard";
        }
    }

    @GetMapping("/clients/edit")
    public String editUser(@RequestParam String clientId, ModelMap model) {
        try {
            UserInfoDTO client = this.userService.getById(clientId);
            model.addAttribute("client", client);
            return "client_panel";
        } catch (MyException ex) {
            return "client_panel";
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

    @GetMapping("/suppliers/edit")
    public String editSupplier(@RequestParam String supplierId, ModelMap model) {
        try {
            SupplierInfoDTO supplier = this.supplierService.getById(supplierId);
            model.addAttribute("supplier", supplier);
            return "supplier_panel";
        } catch (MyException ex) {
            return "supplier_panel";
        }
    }

    @GetMapping("/categories")
    public String categories(ModelMap model) {
        List<CategoryInfoDTO> categories = this.categoryService.listAll();
        model.addAttribute("categories", categories);
        return "categories_dashboard";
    }

    @GetMapping("/category/edit")
    public String getEditCategory(@RequestParam String categoryId, ModelMap model) {
        try {
            CategoryInfoDTO categoryInfo = this.categoryService.getById(categoryId);
            model.addAttribute("category", categoryInfo);
            return "edit_category";

        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/category/edit/{categoryId}")
    public String postEditCategory(@PathVariable String categoryId, @RequestParam String name,
            @RequestParam(required = false) MultipartFile image, ModelMap model) {
        try {
            CategoryInfoDTO categoryInfo = this.categoryService.edit(new EditCategoryDTO(categoryId, name, image));
            /*
             * model.addAttribute("category", categoryInfo);
             * return "edit_category";
             */
            return this.categories(model);

        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/category/create")
    public String getCreateCategory() {
        return "new_category";
    }

    @PostMapping("/category/create")
    public String postCreateCategory(@RequestParam String name, @RequestParam MultipartFile image, ModelMap model) {
        try {
            CategoryInfoDTO category = this.categoryService.save(new SaveCategoryDTO(name, image));
            return this.categories(model);
        } catch (MyException e) {
            model.put("error", e.getMessage());
            return "new_category";
        }

    }
}
