package com.atuservicio.atuservicio.dtos.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EditServiceInfoDTO {
    private String email;
    private String description;
    private Double priceHour;
    private List<MultipartFile> images;
    private List<String> deletedImages;
    private MultipartFile card;
}
