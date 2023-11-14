package com.atuservicio.atuservicio.dtos.users;

import com.atuservicio.atuservicio.dtos.users.UserDTO;
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
    // Se usa para solicitar informacion de un usuario ya creado, tiene los mismos
    // atributos que
    // UserDTO, pero SIN la contraseña y se agrega el id del usuario ya existente
    private String id;
    private String image;
    private Role role;
    private Boolean active;
    private String city;
    private String province;
    private String country;
    private String address;
    private Long address_number;
    private String postal_code;

    public UserInfoDTO(String name, String email, Role role, String image, String address, Long address_number,
            String city, String province, String country, String postal_code, String id, Boolean active) {
        super(name, email);
        this.id = id;
        this.image = image;
        this.role = role;
        this.active = active;
        this.city = city;
        this.province = province;
        this.country = country;
        this.address = address;
        this.address_number = address_number;
        this.postal_code = postal_code;
    }
}
