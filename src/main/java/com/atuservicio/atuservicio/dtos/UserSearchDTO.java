package com.atuservicio.atuservicio.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserSearchDTO {
    private String country;
    private String province;
    private String city;


}
