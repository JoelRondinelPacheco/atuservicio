/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.dtos.categories.EditCategoryDTO;
import com.atuservicio.atuservicio.dtos.categories.SaveCategoryDTO;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.CategoryRepository;
import com.atuservicio.atuservicio.services.interfaces.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author PC
 */
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryInfoDTO save(SaveCategoryDTO category) {
        return null;
    }

    @Override
    public CategoryInfoDTO getById(String id) throws MyException {
        return null;
    }

    @Override
    public List<CategoryInfoDTO> listAll() {
        return null;
    }

    @Override
    public CategoryInfoDTO edit(EditCategoryDTO category) throws MyException {
        return null;
    }

    @Override
    public String delete(String categoryId) throws MyException {
        return null;
    }
}
