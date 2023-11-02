package com.atuservicio.atuservicio.services.interfaces;

import com.atuservicio.atuservicio.entities.Image;
import com.atuservicio.atuservicio.exceptions.MyException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image save(MultipartFile archive) throws MyException;
    Image getById(String id) throws MyException;
    Image update(MultipartFile archive, String id) throws MyException;

}
