package com.atuservicio.atuservicio.dtos.users;

import com.atuservicio.atuservicio.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserSearchDTO {
    private String country;
    private String province;
    private String city;
    private String email;
    private Role rol;
    public UserSearchDTO(String city, String province, String country) {
        
        this.setCity(city);
        this.setCountry(country);
        this.setProvince(province);
        
       
    }
    public UserSearchDTO(String city, String province, String country,String email,Role rol) {
        
        this.setCity(city);
        this.setCountry(country);
        this.setProvince(province);
        this.setEmail(email);
        this.setRol(rol);
    }
}
