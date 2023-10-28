package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.entities.Image;
import com.atuservicio.atuservicio.exceptions.MyException;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    Image save(MultipartFile archive) throws MyException;
    Image update(MultipartFile archive, String id) throws MyException;
    Image getById(String id) throws MyException;
}
