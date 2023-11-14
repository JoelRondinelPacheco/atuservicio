package com.atuservicio.atuservicio.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveUserDTO extends UserDTO {

    private String password;
    private String password2;
    public SaveUserDTO(String name, String email,String password, String password2) {
        super(name, email);
        this.password = password;
        this.password2 = password2;
    }


}
