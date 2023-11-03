package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.dtos.categories.EditCategoryDTO;
import com.atuservicio.atuservicio.dtos.categories.SaveCategoryDTO;
import com.atuservicio.atuservicio.entities.Category;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.CategoryRepository;
import com.atuservicio.atuservicio.services.interfaces.ICategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {
    @Autowired

    CategoryRepository categoryRepository;

    @Override
    public CategoryInfoDTO save(SaveCategoryDTO category) {
        Category categoria = new Category();
        categoria.setName(category.getName());
        Category categoria2 = categoryRepository.save(categoria);
        CategoryInfoDTO categoryInfoDTO = new CategoryInfoDTO(categoria2.getId(), categoria2.getName());
        return categoryInfoDTO;
    }

    @Override
    public CategoryInfoDTO getById(String id) throws MyException {
        CategoryInfoDTO categoryInfoDTO = new CategoryInfoDTO();
        Optional<Category> respuesta = categoryRepository.findById(id);
        if(respuesta.isPresent()){
            Category categoria = respuesta.get();
            categoryInfoDTO.setId(categoria.getId());
            categoryInfoDTO.setName(categoria.getName());
            return categoryInfoDTO;
        }
        throw new MyException("Categoria no encontrada");
}
    

    @Override
    public List<CategoryInfoDTO> listAll() {
        List<Category> categorias = new ArrayList();
        categorias = categoryRepository.findAll();
        List<CategoryInfoDTO> categoryInfoDTO = new ArrayList();
        for (Category c : categorias) {
            CategoryInfoDTO ciDTO = new CategoryInfoDTO(c.getId(), c.getName());
            categoryInfoDTO.add(ciDTO);
        };
        return categoryInfoDTO;
    }

    @Override
    public CategoryInfoDTO edit(EditCategoryDTO category) throws MyException {
        Optional<Category> respuesta = categoryRepository.findById(category.getId());
           if(respuesta.isPresent()){
               Category categoria = respuesta.get();
               categoria.setName(category.getName());
               Category categoria2 = categoryRepository.save(categoria);
               return new CategoryInfoDTO(categoria2.getId(), categoria2.getName());
           }
           throw new MyException("La categoria no fue encontrada");
    }

    @Override
    public String delete(String categoryId) throws MyException {
        categoryRepository.deleteById(categoryId);
        return "Categoria eliminada";
    }
    
}
