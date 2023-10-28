package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.entities.Role;
import com.atuservicio.atuservicio.exceptions.MyException;

public interface IRoleService {
    Role save(String role);
    Role getById(String id) throws MyException;
}
