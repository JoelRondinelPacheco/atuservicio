

package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmailUser(@Param("email") String email);
    
    @Query("SELECT u FROM User u WHERE u.city = :city OR u.province = :province OR u.country = :country")
    public List<User> findByCityProvinceCountry(@Param("city") String city,
                                                @Param("province") String province,
                                                @Param("country") String country);

    

}
