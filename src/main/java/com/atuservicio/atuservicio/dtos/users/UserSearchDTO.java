package com.atuservicio.atuservicio.dtos.users;

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
    public UserSearchDTO(String city, String province, String country) {
        
        this.setCity(city);
        this.setCountry(country);
        this.setProvince(province);
        
       
    }
    public UserSearchDTO(String city, String province, String country,String email) {
        
        this.setCity(city);
        this.setCountry(country);
        this.setProvince(province);
        this.setEmail(email);
       
    }
}
