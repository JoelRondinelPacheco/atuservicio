package com.atuservicio.atuservicio.dtos.services;

import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ServiceInfoDTO {
    private String name;
    private String email;
    //private String phoneNumber;
    private String imageCardId;
    private CategoryInfoDTO category;
    private String description;
    private Double priceHour;
    private List<String> imageGallery;
}
