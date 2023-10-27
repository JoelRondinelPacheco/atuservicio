package com.atuservicio.atuservicio.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoDTO extends UserDTO {
    // Se usa para solicitar informacion de un usuario ya creado, tiene los mismos atributos que
    // UserDTO, pero SIN la contraseña y se agrega el id del usuario ya existente
    private String id;
}
