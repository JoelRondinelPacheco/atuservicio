package com.atuservicio.atuservicio.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterErrorDTO {
    private String name;
    private String nameMsg;
   /* private String email;
    private String password;
    private String password2;
    private String address;
    private Long address_number;
    private String postal_code;
    private String city;
    private String province;
    private String country;*/
}
