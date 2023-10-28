package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.entities.Image;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ImageService implements IImageService{
    @Autowired
    private IImageRepository imageRepository;

    @Override
    @Transactional
    public Image save(MultipartFile archive) throws MyException {
// TODO MANEJAR EXCEPCIONES
        try {
            Image image = new Image();
            image.setMime(archive.getContentType());
            image.setName(archive.getName());
            image.setContent(archive.getBytes());
            return this.imageRepository.save(image);

        } catch (Exception e) {

            throw new MyException("La imagen no pudo ser creada");
        }
    }

    @Override
    public Image update(MultipartFile archive, String id) throws MyException {
        try {
            Image image = new Image();

            if (id != null) {
                Optional<Image> res =  this.imageRepository.findById(id);

                if (res.isPresent()) {
                    image = res.get();
                }
            }

            image.setMime(archive.getContentType());
            image.setName(archive.getName());
            image.setContent(archive.getBytes());

            return this.imageRepository.save(image);

        } catch (Exception e) {
            throw new MyException("La imagen no pudo ser creada");
        }
    }

    @Override
    public Image getById(String id) throws MyException {
        Optional<Image> image = this.imageRepository.findById(id);
        if (image.isPresent()) {
            return image.get();
        }
        throw new MyException("Imagen no encontrada");
    }

}