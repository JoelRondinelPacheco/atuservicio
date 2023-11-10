package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.dtos.categories.EditCategoryDTO;
import com.atuservicio.atuservicio.dtos.categories.SaveCategoryDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierPaginatedDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserPaginatedDTO;
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
    @GetMapping("/dashboard")
    public String adminDashboard(ModelMap model) {
        return this.clientsPaginated(1, "name", "asc", model);
    }
*/
    @GetMapping("/clients/{pageNumber}")
    public String clientsPaginated(@PathVariable int pageNumber, ModelMap model){
            int pageSize = 5;
            UserPaginatedDTO clients = this.userService.findPaginated(pageNumber, pageSize);

            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("totalPages", clients.getTotalPages());
            model.addAttribute("totalItems", clients.getTotalElements());
            model.addAttribute("clients", clients.getClients());
            return "clients_dashboard";
        }

    @GetMapping("/clients/delete")
    public String deleteUser(@RequestParam String clientId, @RequestParam int currentpage, ModelMap model) {
        try {
            this.userService.delete(clientId);
            return this.clientsPaginated(currentpage, model);
           /* List<UserInfoDTO> clients = this.userService.getAllUsers();
            model.addAttribute("clients", clients);
            return "clients_dashboard";*/
        } catch (MyException ex) {
            return "clients_dashboard";
        }
    }

    @GetMapping("/clients/activate")
    public String activateClient(@RequestParam String clientId, @RequestParam int currentpage, ModelMap model) {
        try {
            this.userService.activate(clientId);
            return this.clientsPaginated(currentpage, model);
            /*List<UserInfoDTO> clients = this.userService.getAllUsers();
            model.addAttribute("clients", clients);
            return "clients_dashboard";*/
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
        return "suppliers_dashboard";

        /*
        List<SupplierInfoDTO> suppliers = this.supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "suppliers_dashboard";*/
    }

    @GetMapping("/suppliers/delete")
    public String deleteSupplier(@RequestParam String supplierId, @RequestParam int currentpage, ModelMap model) {
        try {
            this.supplierService.delete(supplierId);
            return this.clientsPaginated(currentpage, model);
        } catch (MyException ex) {
            return "suppliers_dashboard";
        }
    }

    @GetMapping("/suppliers/activate")
    public String activateSupplier(@RequestParam String supplierId,  @RequestParam int currentpage, ModelMap model) {
        try {
            this.supplierService.activate(supplierId);
            return this.clientsPaginated(currentpage, model);
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
    public String postEditCategory(@PathVariable String categoryId, @RequestParam String name, @RequestParam(required = false) MultipartFile image, ModelMap model) {
        try {
            CategoryInfoDTO categoryInfo = this.categoryService.edit(new EditCategoryDTO(categoryId, name, image));
            /*model.addAttribute("category", categoryInfo);
            return "edit_category";*/
            return this.categories(model);

        } catch (MyException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/category/create")
    public String getCreateCategory(){
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
