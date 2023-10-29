package com.atuservicio.atuservicio.dtos;

import com.atuservicio.atuservicio.entities.Image;
import com.atuservicio.atuservicio.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserSearchDTO extends UserDTO {
    // Se usa para solicitar informacion de un usuario ya creado, tiene los mismos atributos que
    // UserDTO, pero SIN la contraseña y se agrega el id del usuario ya existente
    
    public UserSearchDTO(String city, String province, String country) {
        
        this.setCity(city);
        this.setCountry(country);
        this.setProvince(province);
        
       
    }
}
