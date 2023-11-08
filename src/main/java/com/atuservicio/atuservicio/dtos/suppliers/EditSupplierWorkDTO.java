package com.atuservicio.atuservicio.dtos.suppliers;

import com.atuservicio.atuservicio.dtos.users.EditUserDTO;
import com.atuservicio.atuservicio.entities.Image;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditSupplierWorkDTO extends EditUserDTO {
    private String categoryId;
    private String imageCard;
    private List <Image> imageGallery;
    private String description;
    private Long priceHour;
    
    public EditSupplierWorkDTO(String id, String name, MultipartFile image, String address, Long address_number,
            String city, String province, String country, String postal_code, String categoryId) {
        super(id, name, image, address, address_number, city, province, country, postal_code);
        this.categoryId = categoryId;
        this.imageCard = imageCard;
        this.imageGallery = imageGallery;
        this.description = description;
        this.priceHour = priceHour;
    }
}
