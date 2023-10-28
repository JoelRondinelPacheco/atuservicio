package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, String> {
}
