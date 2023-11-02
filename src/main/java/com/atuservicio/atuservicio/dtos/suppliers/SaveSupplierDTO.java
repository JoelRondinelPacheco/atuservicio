package com.atuservicio.atuservicio.dtos.suppliers;

import com.atuservicio.atuservicio.dtos.users.SaveUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveSupplierDTO extends SaveUserDTO {
    private String categoryId;
    public SaveSupplierDTO(String name, String email, String address, Long address_number, String city, String province, String country, String postal_code, String password, String password2, MultipartFile image, String categoryId) {
        super(name, email, address, address_number, city, province, country, postal_code, password, password2, image);
        this.categoryId = categoryId;
    }
}
