package com.atuservicio.atuservicio.services.interfaces;

import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.dtos.categories.EditCategoryDTO;
import com.atuservicio.atuservicio.dtos.categories.SaveCategoryDTO;
import com.atuservicio.atuservicio.exceptions.MyException;

import java.util.List;

public interface ICategoryService {
    CategoryInfoDTO save(SaveCategoryDTO category) throws MyException;
    CategoryInfoDTO getById(String id) throws MyException;
    List<CategoryInfoDTO> listAll();
    CategoryInfoDTO edit(EditCategoryDTO category) throws MyException;
    String delete(String categoryId) throws MyException;
}
