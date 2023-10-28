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
    // Para crear el usuario, extiende de UserDTO y se le agrega la contraseña necesaria al crear el usuario
    // TODO definir como se actualiza la contraseña
    private MultipartFile image;
    private String password;
    private String password2;
    private String role;

    public SaveUserDTO(String name, String email, String role, MultipartFile image, String address, Long address_number, String city, String province, String country, String postal_code, String password, String password2) {
        super(name, email, address, address_number, city, province, country, postal_code);
        this.password = password;
        this.password2 = password2;
        this.image = image;
        this.role = role;
    }


}
