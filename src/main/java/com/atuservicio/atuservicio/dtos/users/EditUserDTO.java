package com.atuservicio.atuservicio.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditUserDTO extends UserDTO {
    private MultipartFile image;
    private String id;
    public EditUserDTO(String name, String email, MultipartFile image, String address, Long address_number, String city, String province, String country, String postal_code, String id) {
        super(name, email, address, address_number, city, province, country, postal_code);
        this.image = image;
        this.id = id;
    }
}
