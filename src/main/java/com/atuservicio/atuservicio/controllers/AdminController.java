package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.dtos.categories.EditCategoryDTO;
import com.atuservicio.atuservicio.dtos.categories.SaveCategoryDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierPaginatedDTO;
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
        return this.clientsPageDashboard(1, true, model);
    }
    @GetMapping("/clients/{pageNumber}")
    public String clientsPageDashboard(@PathVariable int pageNumber, @RequestParam Boolean active, ModelMap model) {
        int pageSize = 5;
        UserPaginatedDTO clients = this.userService.
                findPaginated(pageNumber, pageSize, Role.CLIENT, active);
        Role[] roles = Role.values();
        model.addAttribute("roles", roles);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", clients.getTotalPages());
        model.addAttribute("totalItems", clients.getTotalElements());
        model.addAttribute("clients", clients.getClients());
        model.put("currentRole", "CLIENT");
        if (active) {
            model.put("active", "active");
            return "admin_dashboard/clients_dashboard";
        } else {
            model.put("active", "inactive");
            return "admin_dashboard/clients_dashboard_inactive";
        }
    }

    @GetMapping("/suppliers")
    public String suppliersMainDashboard(ModelMap model) {
        return this.supplierPageDashboard(1, true, model);
    }

    @GetMapping("/suppliers/{pageNumber}")
    public String supplierPageDashboard(@PathVariable int pageNumber,
                                        @RequestParam Boolean active,
                                        ModelMap model) {
        int pageSize = 5;
        SupplierPaginatedDTO suppliers = this.supplierService.findPaginatedByActive(pageNumber, pageSize, active);

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", suppliers.getTotalPages());
        model.addAttribute("totalItems", suppliers.getTotalElements());
        model.addAttribute("suppliers", suppliers.getSuppliers());
        model.put("currentRole", "SUPPLIER");
        if (active) {
            model.put("active", "active");
            return "admin_dashboard/suppliers_dashboard";
        } else {
            model.put("active", "inactive");
            return "admin_dashboard/suppliers_dashboard_inactive";
        }

    }

    @GetMapping("/moderators")
    public String moderatorsMainDashboard(ModelMap model) {
        return this.moderatorsPageDashboard(1, true, model);
    }
    @GetMapping("/moderators/{pageNumber}")
    public String moderatorsPageDashboard(@PathVariable int pageNumber, @RequestParam Boolean active, ModelMap model) {
        int pageSize = 5;
        UserPaginatedDTO moderators = this.userService.findPaginated(pageNumber, pageSize, Role.MODERATOR, active);

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", moderators.getTotalPages());
        model.addAttribute("totalItems", moderators.getTotalElements());
        model.addAttribute("moderators", moderators.getClients());
        model.put("currentRole", "MODERATOR");
        if (active) {
            model.put("active", "active");
            return "admin_dashboard/moderators_dashboard";
        } else {
            model.put("active", "inactive");
            return "admin_dashboard/moderators_dashboard_inactive";
        }

    }


    @GetMapping("/admins")
    public String adminsMainDashboard(ModelMap model) {
        return this.adminsPageDashboard(1, true, model);
    }
    @GetMapping("/admins/{pageNumber}")
    public String adminsPageDashboard(@PathVariable int pageNumber, @RequestParam Boolean active, ModelMap model) {
        int pageSize = 5;
        UserPaginatedDTO admins = this.userService.findPaginated(pageNumber, pageSize, Role.ADMIN, active);

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", admins.getTotalPages());
        model.addAttribute("totalItems", admins.getTotalElements());
        model.addAttribute("admins", admins.getClients());
        model.put("currentRole", "ADMIN");
        if (active) {
            model.put("active", "active");
            return "admin_dashboard/admins_dashboard";
        } else {
            model.put("active", "inactive");
            return "admin_dashboard/admins_dashboard_inactive";
        }
    }

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

    @GetMapping("/clients/delete")
    public String deleteUser(@RequestParam String clientId, @RequestParam int currentpage, ModelMap model) {
        try {
            this.userService.delete(clientId);
            model.put("clientDeleted","Cliente dado de baja correctamente");
            return this.clientsPageDashboard(currentpage, true, model);
        } catch (MyException ex) {
            return "admin_dashboard/clients_dashboard";
        }
    }

    @GetMapping("/clients/activate")
    public String activateClient(@RequestParam String clientId, @RequestParam int currentpage, ModelMap model) {
        try {
            this.userService.activate(clientId);
            model.put("clientActivated","Cliente dado de alta correctamente");
            return this.clientsPageDashboard(currentpage, false, model);
        } catch (MyException ex) {
            return "admin_dashboard/clients_dashboard";
        }
    }
    //CONVERTIR A USER
    @GetMapping("/role/client/{userId}/{returnTo}")
    public String makeUser(@PathVariable String userId, @PathVariable String returnTo, @RequestParam int currentpage, ModelMap model) {
        try {
            this.userService.changeRole(userId, Role.CLIENT);
            return this.returnTo(returnTo, currentpage, model);
        } catch (MyException ex) {
            return null;
        }
    }

    //CONVERTIR A MODERADOR
    @GetMapping("/role/moderator/{userId}/{returnTo}")
    public String makeModerator(@PathVariable String userId, @PathVariable String returnTo, @RequestParam int currentpage, ModelMap model) {
        try {
            this.userService.changeRole(userId, Role.MODERATOR);
            return this.returnTo(returnTo, currentpage, model);
        } catch (MyException ex) {
            return null;
        }
    }
    //CONVERTIR A ADMIN
    @GetMapping("/role/admin/{userId}/{returnTo}")
    public String makeAdmin(@PathVariable String userId, @PathVariable String returnTo, @RequestParam int currentpage, ModelMap model) {
        try {
            this.userService.changeRole(userId, Role.ADMIN);
            return this.returnTo(returnTo, currentpage, model);
        } catch (MyException ex) {
            return null;
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
    /////////////////////////////////////////////////////////////////////////////

    @GetMapping("/suppliers/delete")
    public String deleteSupplier(@RequestParam String supplierId, @RequestParam int currentpage, ModelMap model) {
        try {
            this.supplierService.delete(supplierId);
            model.put("supplierDeleted","Proveedor dado de baja correctamente");
            return this.supplierPageDashboard(currentpage, true, model);
        } catch (MyException ex) {
            return "admin_dashboard/suppliers_dashboard";
        }
    }

    @GetMapping("/suppliers/activate")
    public String activateSupplier(@RequestParam String supplierId,  @RequestParam int currentpage, ModelMap model) {
        try {
            this.supplierService.activate(supplierId);
            model.put("supplierActivated","Proveedor dado de alta correctamente");
            return this.supplierPageDashboard(currentpage, false, model);
        } catch (MyException ex) {
            return "admin_dashboard/suppliers_dashboard";
        }
    }
///////////////////////////////////////////////////////
    @GetMapping("/moderators/delete")
    public String deleteModerator(@RequestParam String moderatorId, @RequestParam int currentpage, ModelMap model) {
        try {
            this.userService.delete(moderatorId);
            model.put("moderatorDeleted", "Moderador dado de baja correctamente");
            return this.moderatorsPageDashboard(currentpage, true, model);
        } catch (MyException ex) {
            return "admin_dashboard/moderators_dashboard";
        }
    }

    @GetMapping("/moderators/activate")
    public String activateModerator(@RequestParam String moderatorId,  @RequestParam int currentpage, ModelMap model) {
        try {
            this.userService.activate(moderatorId);
            model.put("moderatorActivated", "Moderador dado de alta correctamente");
            return this.moderatorsPageDashboard(currentpage, false, model);
        } catch (MyException ex) {
            return "admin_dashboard/moderators_dashboard";
        }
    }

    ////////////////////////////////////////////////////////////
    @GetMapping("/admins/delete")
    public String deleteAdmin(@RequestParam String adminId, @RequestParam int currentpage, ModelMap model) {
        try {
            this.userService.delete(adminId);
            model.put("adminDeleted", "Administrador dado de baja correctamentte");
            return this.adminsPageDashboard(currentpage, true, model);
        } catch (MyException ex) {
            return "admin_dashboard/admins_dashboard";
        }
    }

    @GetMapping("/admins/activate")
    public String activateAdmin(@RequestParam String adminId,  @RequestParam int currentpage, ModelMap model) {
        try {
            this.userService.activate(adminId);
            model.put("adminActivated", "Administrador dado de alta correctamentte");
            return this.adminsPageDashboard(currentpage, false, model);
        } catch (MyException ex) {
            return "admin_dashboard/admins_dashboard";
        }
    }
    /////////////////////////////////////////////////////
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
        model.put("currentRole", "CATEGORY");
        return "admin_dashboard/categories_dashboard";
    }

    @GetMapping("/category/edit")
    public String getEditCategory(@RequestParam String categoryId, ModelMap model) {
        try {
            CategoryInfoDTO categoryInfo = this.categoryService.getById(categoryId);
            model.addAttribute("category", categoryInfo);
            return "admin_dashboard/edit_category";

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
        return "admin_dashboard/new_category";
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

    private String returnTo(String page, int currentpage, ModelMap model) {
        switch(page) {
            case "clients":
                return this.clientsPageDashboard(currentpage, true, model);
            case "suppliers":
                return this.supplierPageDashboard(currentpage, true, model);
            case "moderators":
                return this.moderatorsPageDashboard(currentpage, true, model);
            case "admins":
                return this.adminsPageDashboard(currentpage, true, model);
            default:
                return this.clientsPageDashboard(currentpage, true, model);
        }
    }
}
