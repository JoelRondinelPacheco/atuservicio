package com.atuservicio.atuservicio.dtos.suppliers;

import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.entities.Category;
import com.atuservicio.atuservicio.entities.Image;
import com.atuservicio.atuservicio.enums.Role;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierInfoDTO extends UserInfoDTO {
    private Category category;
    private String imageCard;
   /* private List <Image> imageGallery;
    private String description;
    private Long priceHour;*/
    
    public SupplierInfoDTO(String name, String email, Role role, String image, String address, Long address_number, String city, String province, String country, String postal_code, String id, Category category, Boolean active, String imageCard) {
        super(name, email, role, image, address, address_number, city, province, country, postal_code, id, active);
        this.category = category;
        this.imageCard = imageCard;
        
    }
}
