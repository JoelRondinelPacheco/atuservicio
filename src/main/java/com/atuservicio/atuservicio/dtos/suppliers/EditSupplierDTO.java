package com.atuservicio.atuservicio.dtos.suppliers;

import com.atuservicio.atuservicio.dtos.users.EditUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditSupplierDTO extends EditUserDTO {
    private String categoryId;
    public EditSupplierDTO(String name, String email, MultipartFile image, String address, Long address_number, String city, String province, String country, String postal_code, String id, String categoryId) {
        super(name, email, image, address, address_number, city, province, country, postal_code, id);
        this.categoryId = categoryId;
    }
}
