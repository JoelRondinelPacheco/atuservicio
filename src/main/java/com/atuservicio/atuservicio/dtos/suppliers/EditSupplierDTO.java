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

    public EditSupplierDTO(String id, String name, MultipartFile image, String address, Long address_number,
            String city, String province, String country, String postal_code, String categoryId) {
        super(id, name, image, address, address_number, city, province, country, postal_code);
        this.categoryId = categoryId;
    }
}
