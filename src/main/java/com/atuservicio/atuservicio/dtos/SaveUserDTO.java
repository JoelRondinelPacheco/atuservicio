package com.atuservicio.atuservicio.dtos;

import com.atuservicio.atuservicio.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveUserDTO extends UserDTO {

    private String password;
    private String password2;
    private MultipartFile image;
    public SaveUserDTO(String name, String email, String address, Long address_number, String city, String province, String country, String postal_code, String password, String password2, MultipartFile image) {
        super(name, email, address, address_number, city, province, country, postal_code);
        this.password = password;
        this.password2 = password2;
        this.image = image;
    }


}
