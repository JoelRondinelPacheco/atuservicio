package com.atuservicio.atuservicio.dtos.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EditCategoryDTO {
    
    private String id;
    private String name;
    private MultipartFile image;
    
}
