

package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    /*
    @Query("SELECT u FROM users u WHERE u.email = :email")
    public User findByEmail(@Param("email")String email);*/
}
