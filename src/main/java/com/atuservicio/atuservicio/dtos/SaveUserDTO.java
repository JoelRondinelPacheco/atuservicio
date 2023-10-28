package com.atuservicio.atuservicio.dtos;

import com.atuservicio.atuservicio.entities.Role;
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
    // Para crear el usuario, extiende de UserDTO y se le agrega la contraseña necesaria al crear el usuario
    // TODO definir como se actualiza la contraseña
    private String password;
    private String password2;


}
