package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.dtos.categories.EditCategoryDTO;
import com.atuservicio.atuservicio.dtos.categories.SaveCategoryDTO;
import com.atuservicio.atuservicio.entities.Category;
import com.atuservicio.atuservicio.entities.Image;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.CategoryRepository;
import com.atuservicio.atuservicio.repositories.IImageRepository;
import com.atuservicio.atuservicio.services.interfaces.ICategoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.atuservicio.atuservicio.services.interfaces.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private IImageRepository imageRepository;
    @Autowired
    private IImageService imageService;

    @Override
    public CategoryInfoDTO save(SaveCategoryDTO category) throws MyException {
        if ((category.getName() == null || category.getName().isEmpty()) || category.getImage().isEmpty()) {
            throw new MyException("Datos invalidos");
        }
        Category categoria = new Category();
        categoria.setName(category.getName());
        Image img = this.imageService.save(category.getImage());
        categoria.setImage(img);
        Category categoria2 = categoryRepository.save(categoria);
        CategoryInfoDTO categoryInfoDTO = new CategoryInfoDTO(categoria2.getId(), categoria2.getName(), img.getId());
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
            categoryInfoDTO.setImage_id(categoria.getImage().getId());
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
            CategoryInfoDTO ciDTO = new CategoryInfoDTO(c.getId(), c.getName(), c.getImage().getId());
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
               if(!category.getImage().isEmpty()) {
                   try {
                   Image img = categoria.getImage();
                       img.setContent(category.getImage().getBytes());
                       this.imageRepository.save(img);
                       //TODO REVISAR QUE FUNCIONE
                   } catch (IOException e) {
                       throw new MyException(e.getMessage());
                   }
               }
               Category categoria2 = categoryRepository.save(categoria);
               return new CategoryInfoDTO(categoria2.getId(), categoria2.getName(), categoria2.getImage().getId());
           }
           throw new MyException("La categoria no fue encontrada");
    }

    @Override
    public String delete(String categoryId) throws MyException {
        categoryRepository.deleteById(categoryId);
        return "Categoria eliminada";
    }



}
