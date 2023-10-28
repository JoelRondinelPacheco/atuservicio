package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.entities.Role;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService{
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Role save(String role) {
        Role roleEntity = new Role(role);
        return this.roleRepository.save(roleEntity);
    }

    @Override
    public Role getById(String id) throws MyException {
        Optional<Role> roleOptional = this.roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            return roleOptional.get();
        }
        throw new MyException("Role no encontrado");
    }
}
