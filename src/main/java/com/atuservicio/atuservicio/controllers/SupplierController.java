/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.dtos.suppliers.EditSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SaveSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.interfaces.ICategoryService;
import com.atuservicio.atuservicio.services.interfaces.ISupplierService;
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
@RequestMapping("/supplier")
public class SupplierController {
    
    @Autowired
    ISupplierService supplierService;
    
    @Autowired
    ICategoryService categoryService;

    @GetMapping("/register")
    public String register(ModelMap model) {
        
        List <CategoryInfoDTO> categories = categoryService.listAll();
        
        model.addAttribute("categories", categories);

        return "register_supplier.html";
    }
    
    
    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email, 
            @RequestParam String password, @RequestParam String password2, @RequestParam(required = false) MultipartFile image,
            @RequestParam String address,@RequestParam Long address_number,@RequestParam String postal_code,
            @RequestParam String city,@RequestParam String province, @RequestParam String country, @RequestParam String categoryId, ModelMap model) throws MyException {
            
            

        try {
            SaveSupplierDTO user = new SaveSupplierDTO(name, email, address, address_number, city,
                    province, country, postal_code, password, password2, image, categoryId);
            
            supplierService.save(user);
            model.put("exito", "usuario registrado correctamente");
            return "redirect:/login";
        
        } catch (MyException ex){
           model.put("error", ex.getMessage());
           model.put("name", name);
           model.put("email", email);
           return "register_supplier.html";
        }

    }
    
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") String id, ModelMap model) throws MyException {
        //TODO Manejar excepcion si no hay usuario con el id proporcionado
        model.addAttribute("supplier", supplierService.getById(id));

        return "supplier_modify.html";
    }
    
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") String id, String name, String email, MultipartFile image, String address, Long address_number, String city, String province, String country, String postal_code, String categoryId, ModelMap model) {

        try {

            EditSupplierDTO supplierInfoDTO = new EditSupplierDTO(id, name, image, address, address_number, country, province, city, postal_code, categoryId);
            supplierService.edit(supplierInfoDTO);
            model.put("exito", "Se actualizaron los datos correctamente");

        } catch (MyException ex) {

            model.put("error", ex.getMessage());

            return "supplier_modify.html";

        }

        return "index.html";
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
    public String list(ModelMap model){
        
        List<SupplierInfoDTO> users = supplierService.getAllSuppliers();
        
        model.addAttribute("users", users);
       
        return "supplier_list.html";
    }

    @GetMapping("/services")
    public String listServices(ModelMap model){

        List<SupplierInfoDTO> users = supplierService.getAllSuppliers();

        model.addAttribute("users", users);

        return "services.html";
    }


    @GetMapping("/profile/{id}")
    public String profile(@PathVariable("id") String id, ModelMap model) throws MyException{
        
        SupplierInfoDTO user = supplierService.getById(id);
        
       model.addAttribute("user", user);
       
        return "supplier_detail.html";
        
    }
    

}
