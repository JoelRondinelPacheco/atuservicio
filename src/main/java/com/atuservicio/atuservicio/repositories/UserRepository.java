

package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
<<<<<<< HEAD

    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmail(@Param("email")String email);

    @Query("SELECT u FROM User u WHERE u.country = :userCountry")
    List<User> findUsersByCountry(@Param("userCountry") String userCountry);

    @Query("SELECT u FROM User u WHERE u.country = :userCountry AND u.city = :userCity")
    List<User> findUsersByCountryCity(@Param("userCountry") String userCountry, @Param("userCity") String userCity);

    @Query("SELECT u FROM User u WHERE u.country = :userCountry AND u.city = :userCity AND u.province = :userProvince")
    List<User> findUsersByCountryCityProvince(@Param("userCountry") String userCountry, @Param("userCity") String userCity, @Param("userProvince") String userProvince);

    @Query("SELECT u FROM User u WHERE u.postal_code = :userPostal")
    List<User> findUsersByPostalCode(@Param("userPostal") String userPostal);
=======
    
    @Query("SELECT u FROM User u WHERE u.email = :email")
    public User findByEmailUser(@Param("email") String email);
    
    @Query("SELECT u FROM User u WHERE u.city = :city OR u.province = :province OR u.country = :country")
    public List<User> findByCityProvinceCountry(@Param("city") String city,
                                                @Param("province") String province,
                                                @Param("country") String country);

    
>>>>>>> developer-sprint1

}
