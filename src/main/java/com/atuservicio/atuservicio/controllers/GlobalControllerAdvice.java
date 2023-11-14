/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.services.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author facun
 */
@ControllerAdvice
public class GlobalControllerAdvice { //Esta clase inyecta las categorias 
                                      //en todas la vistas sin necesidad de repetir codigo

    @Autowired
    CategoryService categoryService;

    @ModelAttribute("categories")
    public List<CategoryInfoDTO> getCategories() {

        List<CategoryInfoDTO> categories = this.categoryService.listAll();

        return categories;
    }
    
}
