package com.atuservicio.atuservicio.dtos;

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

    public UserSearchDTO(String city, String province, String country) {
        
        this.setCity(city);
        this.setCountry(country);
        this.setProvince(province);
        
       
    }
}
