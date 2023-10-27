package com.atuservicio.atuservicio.dtos;

import com.atuservicio.atuservicio.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    // Solo esta creada como clase padre, para que heredan las demas y no repetir codigo
    private String name;
    private String email;
    private Role role;
    private String image;
    private String address;
    private Long address_number;
    private String city;
    private String province;
    private String country;
    private String postal_code;
}
