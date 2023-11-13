package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImageById (@PathVariable String imageId) throws MyException {
        byte[] imagen= this.imageService.getById(imageId).getContent();


        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen,headers, HttpStatus.OK);
    }
}
