package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.entities.Role;
import com.atuservicio.atuservicio.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    //TODO MANEJAR EXCEPIONES
    public Role getById(String id) {
        return this.roleRepository.findById(id).get();
    }

    public Role findByRole(String role) {
        return this.roleRepository.findByRole(role);
    }
}
