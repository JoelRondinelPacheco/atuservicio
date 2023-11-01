package com.atuservicio.atuservicio.dtos;

import com.atuservicio.atuservicio.entities.Image;
import com.atuservicio.atuservicio.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoDTO extends UserDTO {
    // Se usa para solicitar informacion de un usuario ya creado, tiene los mismos atributos que
    // UserDTO, pero SIN la contraseña y se agrega el id del usuario ya existente
    private String id;
    private Image image;
    private Role role;
    public UserInfoDTO(String name, String email, Role role, Image image, String address, Long address_number, String city, String province, String country, String postal_code, String id) {
        super(name, email, address, address_number, city, province, country, postal_code);
        this.id = id;
        this.image = image;
        this.role = role;
    }
}
