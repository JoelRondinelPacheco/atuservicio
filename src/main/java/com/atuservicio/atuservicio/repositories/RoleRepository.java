package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    //TODO AREGLAR QUERY
    Role findByRole(String role);
}
