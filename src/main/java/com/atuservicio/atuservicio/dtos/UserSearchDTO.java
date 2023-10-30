package com.atuservicio.atuservicio.dtos;

<<<<<<< HEAD
=======
import com.atuservicio.atuservicio.entities.Image;
import com.atuservicio.atuservicio.entities.Role;
>>>>>>> developer-sprint1
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserSearchDTO {
    private String country;
    private String province;
    private String city;


=======

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
>>>>>>> developer-sprint1
}
