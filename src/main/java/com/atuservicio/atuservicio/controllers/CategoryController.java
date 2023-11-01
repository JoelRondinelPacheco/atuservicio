/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.exceptions.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author dario
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
    
    @Autowired
    CategoryService categoryService;
    
    @GetMapping("/search/{category_id}")
    public String findCategoryById(@PathVariable("id") String id, ModelMap model) throws MyException{
        
        CategoryInfoDTO category = categoryService.getById(id);
        
        model.addAttribute("category", category);
       
        return "search_category.html";
        
    }    
   
    
}
