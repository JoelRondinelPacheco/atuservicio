/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.LoginPassDTO;
import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.dtos.services.EditServiceInfoDTO;
import com.atuservicio.atuservicio.dtos.services.ServiceInfoDTO;
import com.atuservicio.atuservicio.dtos.suppliers.EditSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SaveSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserRegisterErrorDTO;
import com.atuservicio.atuservicio.dtos.users.UserSearchDTO;
import com.atuservicio.atuservicio.entities.Category;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.SupplierService;
import com.atuservicio.atuservicio.services.interfaces.ICategoryService;
import com.atuservicio.atuservicio.services.interfaces.ISupplierService;
import com.atuservicio.atuservicio.services.interfaces.IUserService;
import java.util.ArrayList;
import java.util.List;

import com.atuservicio.atuservicio.utils.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;
    @Autowired
    IUserService userService;
    @Autowired
    ICategoryService categoryService;
    @Autowired
    private PasswordValidator passwordValidator;

    @GetMapping("/register")
    public String register(ModelMap model) {

        List<CategoryInfoDTO> categories = categoryService.listAll();

        model.addAttribute("categories", categories);

        return "register_supplier.html";
    }

    @PostMapping("/register")
    public String register(@RequestParam(required = false) String name, @RequestParam(required = false) String email,
            @RequestParam(required = false) String password, @RequestParam(required = false) String password2,
            @RequestParam(required = false) MultipartFile image, @RequestParam(required = false) String address,
            @RequestParam(required = false) Long address_number, @RequestParam(required = false) String postal_code,
            @RequestParam(required = false) String city, @RequestParam(required = false) String province,
            @RequestParam(required = false) String country, @RequestParam(required = false) String categoryId,
            ModelMap model) throws MyException {

        if (city == null) {
            city = "";
        }
        System.out.println("rubro:" + categoryId);
        try {

            List<UserRegisterErrorDTO> errors = validar(name, email, password,
                    password2, address, address_number, postal_code, city, province,
                    country, categoryId);

            if (!errors.isEmpty()) {
                model.addAttribute("errors", errors);
                model.put("name", name);
                model.put("email", email);
                model.put("address", address);
                model.put("address_number", address_number);
                model.put("postal_code", postal_code);
                model.put("city", city);
                model.put("province", province);
                model.put("country", country);
                model.put("categoryId", categoryId);

                List<CategoryInfoDTO> categories = categoryService.listAll();

                model.addAttribute("categories", categories);

                return "register_supplier.html";
            }

            SaveSupplierDTO user = new SaveSupplierDTO(name, email, address, address_number, city,
                    province, country, postal_code, password, password2, image, categoryId);

            supplierService.save(user);
            model.put("exito", "usuario registrado correctamente");
            return "redirect:/login";

        } catch (MyException ex) {
            System.out.println(ex);
            model.put("error", ex.getMessage());
            model.put("name", name);
            model.put("email", email);
            return "register_supplier.html";
        }

    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") String id, ModelMap model) throws MyException {
        // TODO Manejar excepcion si no hay usuario con el id proporcionado
        model.addAttribute("supplier", supplierService.getById(id));
        model.addAttribute("categories", categoryService.listAll());
        CategoryInfoDTO category = categoryService.getById(supplierService.getById(id).getCategory().getId());
        model.put("category", category);
        return "supplier_panel.html";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") String id, @RequestParam String name, @RequestParam MultipartFile image,
            @RequestParam String address,
            @RequestParam Long address_number, @RequestParam String city, @RequestParam String province,
            @RequestParam String country, @RequestParam String postal_code, @RequestParam String categoryId,
            ModelMap model) {

        try {

            EditSupplierDTO supplierInfoDTO = new EditSupplierDTO(id, name, image, address, address_number, city,
                    province, country, postal_code, categoryId);
            SupplierInfoDTO supplier = this.supplierService.edit(supplierInfoDTO);
            model.addAttribute("exito", "Datos actualizados");
            model.addAttribute("user", supplier);
            return "supplier_profile";
            /*
             * List<CategoryInfoDTO> categories = categoryService.listAll();
             * CategoryInfoDTO category = categoryService.getById(categoryId);
             * model.put("category", category);
             * model.addAttribute("categories", categories);
             * model.put("updated", "Se actualizaron los datos correctamente");
             * model.addAttribute("supplier", supplierInfoDTO);
             */
        } catch (MyException ex) {

            model.put("error", ex.getMessage());

            return "supplier_modify.html";

        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, ModelMap model) {

        try {

            supplierService.delete(id);

            model.put("exito", "Se eliminó el proveedor correctamente");

        } catch (MyException ex) {

            model.put("error", ex.getMessage());

            return "supplier_modify.html";
        }

        return "suppler_list.html";
    }

    @GetMapping("/list")
    public String list(ModelMap model) {

        List<SupplierInfoDTO> users = supplierService.getAllSuppliers();

        model.addAttribute("users", users);

        return "supplier_list.html";
    }

    @GetMapping("/services")
    public String listServices(ModelMap model) {

        List<SupplierInfoDTO> users = supplierService.getAllSuppliers();
        List<CategoryInfoDTO> categories = this.categoryService.listAll();
        model.addAttribute("categories", categories);
        model.addAttribute("users", users);
        model.addAttribute("locationFound", true);

        return "services.html";
    }

    @PostMapping("/services")
    public String searchServices(@RequestParam(required = false) String country,
            @RequestParam(required = false) String province, @RequestParam(required = false) String city,
            @RequestParam(required = false) String email,@RequestParam(required = false)String category, ModelMap model) {

        if (email.isEmpty()) {
            System.out.println(province);
            System.out.println(country);
            System.out.println(city);
            UserSearchDTO userSearch = new UserSearchDTO(city, province, country);
            List<SupplierInfoDTO> users = supplierService.getSearchSuppliers(userSearch,category);

            List<CategoryInfoDTO> categories = this.categoryService.listAll();
            model.addAttribute("categories", categories);

            model.addAttribute("locationFound", true);
            model.addAttribute("users", users);

            return "services.html";
        } else {
            try {
                SupplierInfoDTO user = supplierService.getByEmail(email);
                List<CategoryInfoDTO> categories = this.categoryService.listAll();
                model.addAttribute("categories", categories);
                model.addAttribute("userFound", true);
                model.addAttribute("user", user);

                return "services.html";

            } catch (MyException ex) {
                List<CategoryInfoDTO> categories = this.categoryService.listAll();
                model.addAttribute("categories", categories);
                model.put("error", ex.getMessage());
                System.out.println(ex.getMessage());
                return "services.html";
            }
        }

    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable("id") String id, ModelMap model) throws MyException {

        SupplierInfoDTO user = supplierService.getById(id);

        model.addAttribute("user", user);

        return "supplier_profile.html";

    }

    @GetMapping("/workPreview/{id}")
    public String workPreview(@PathVariable("id") String id, ModelMap model) throws MyException {

        SupplierInfoDTO supplier = supplierService.getById(id);
        String email = supplier.getEmail();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        UserInfoDTO user = userService.getSearchEmailUser(userEmail);
        ServiceInfoDTO service = this.supplierService.getServiceInfo(email);
         model.addAttribute("user", user);
        model.addAttribute("supplier", supplier);
        model.addAttribute("service", service);
        return "work_user.html";
    }

    @GetMapping("/service")
    public String myService(ModelMap model) throws MyException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        SupplierInfoDTO supplier = supplierService.getByEmail(email);
        ServiceInfoDTO service = this.supplierService.getServiceInfo(email);
        model.addAttribute("supplier", supplier);
        model.addAttribute("service", service);
        return "work.html";
    }

    @GetMapping("/workEdit")
    public String workEdit(ModelMap model) throws MyException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        ServiceInfoDTO service = this.supplierService.getServiceInfo(email);

        model.addAttribute("service", service);

        return "work_edit.html";

    }

    @PostMapping("/workEdit")

    public String postWorkInfo(@RequestParam String description, @RequestParam Double priceHour, @RequestParam(required = false) List<MultipartFile> images,
                               @RequestParam(required = false) MultipartFile card, @RequestParam(required = false, name = "delete") List<String> delete,  ModelMap model) throws MyException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        try {
            if (delete == null || delete.isEmpty()) {
                delete = new ArrayList<String>();
                delete.add("empty");
            }

            ServiceInfoDTO service = this.supplierService.editServiceInfo(new EditServiceInfoDTO(email, description, priceHour, images, delete, card));
            model.addAttribute("service", service);
            model.addAttribute("exito", "Servicio actualizado correctamente");
            return "work";
        } catch (MyException e) {
            model.addAttribute("error", "Algo salio mal");
            return this.workEdit(model);
        }

    }

    private List<UserRegisterErrorDTO> validar(String name, String email,
            String password, String password2,
            String address, Long address_number, String postal_code,
            String city, String province, String country, String categoryId) throws MyException {
        List<UserRegisterErrorDTO> errors = new ArrayList<>();

        if (name.isEmpty() || name == null) {

            errors.add(new UserRegisterErrorDTO("name", "Nombre requerido"));
        }
        if (email.isEmpty() || email == null) {

            errors.add(new UserRegisterErrorDTO("email", "Email requerido"));
        } else {
            try {
                UserInfoDTO user = userService.getSearchEmailUser(email);
                errors.add(new UserRegisterErrorDTO("email", "El usuario ya está registardo"));
            } catch (MyException ex) {

            }
            // TODO codigo innecesario para validar email, rompe el registro y sin esta
            // parte funciona y valida correctamente el email
        }

        if (password.isEmpty() || password == null) {
            errors.add(new UserRegisterErrorDTO("password", "La contraseña no puede estar vacía"));
        } else {
            if (!this.passwordValidator.isValid(password)) {
                errors.add(new UserRegisterErrorDTO("password",
                        "La contraseña debe contener numeros del 0 al 9, mayusculas y minusculas, y tener entre 5 y 15 caracteres"));
            }
        }

        if (!password.equals(password2)) {
            errors.add(new UserRegisterErrorDTO("password2", "Las contraseñas ingresadas deben ser iguales"));
        }
        if (address.isEmpty() || address == null) {

            errors.add(new UserRegisterErrorDTO("address", "Dirección requerida"));
        }
        if (address_number == null) {

            errors.add(new UserRegisterErrorDTO("address_number", "Altura de dirección requerida"));
        }
        if (postal_code.isEmpty() || postal_code == null) {

            errors.add(new UserRegisterErrorDTO("postal_code", "Codigo postal requerido"));
        }
        if (city.isEmpty() || city == null) {

            errors.add(new UserRegisterErrorDTO("city", "Localidad requerida"));
        }
        if (province.isEmpty() || province == null) {

            errors.add(new UserRegisterErrorDTO("province", "Provincia requerida"));
        }
        if (country.isEmpty() || country == null) {

            errors.add(new UserRegisterErrorDTO("country", "País requerido"));
        }
        if (categoryId.isEmpty() || categoryId == null || categoryId.equals("Selecciona un rubro")) {

            errors.add(new UserRegisterErrorDTO("categoryId", "Rubro requerido"));
        }

        return errors;

    }

}
