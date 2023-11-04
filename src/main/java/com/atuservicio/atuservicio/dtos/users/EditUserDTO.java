package com.atuservicio.atuservicio.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditUserDTO {
    private String id;
    private String name;
    private MultipartFile image;
    private String address;
    private Long address_number;
    private String country;
    private String province;
    private String city;
    private String postal_code;
}
