package com.atuservicio.atuservicio.dtos.users;

import com.atuservicio.atuservicio.enums.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserSearchAdminDTO extends UserSearchDTO {

    private String email;
    private Role role;
    private String name;
    private String categoryId;

    public UserSearchAdminDTO(String city, String province, String country, String email, Role role, String name,String categoryId) {
        super(city, province, country);
        this.email = email;
        this.role = role;
        this.name = name;
        this.categoryId = categoryId;
    }


}
