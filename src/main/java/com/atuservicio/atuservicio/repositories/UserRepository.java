
package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.entities.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.country = :country")
    List<User> findUsersByCountry(@Param("country") String country);

    @Query("SELECT u FROM User u WHERE u.country = :userCountry AND u.city = :userCity")
    List<User> findUsersByCountryCity(@Param("userCountry") String userCountry, @Param("userCity") String userCity);

    @Query("SELECT u FROM User u WHERE u.country = :userCountry AND u.city = :userCity AND u.province = :userProvince")
    List<User> findUsersByCountryCityProvince(@Param("userCountry") String userCountry,
            @Param("userCity") String userCity,
            @Param("userProvince") String userProvince); // Nombre de método similar (consultar)

    @Query("SELECT u FROM User u WHERE u.postal_code = :userPostal")
    List<User> findUsersByPostalCode(@Param("userPostal") String userPostal);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    public Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.city = :city OR u.province = :province OR u.country = :country")
    public List<User> findByCityProvinceCountry(@Param("city") String city,
            @Param("province") String province,
            @Param("country") String country);


    @Query("SELECT u FROM User u WHERE u.city = :city")
    public List<User> findUsersByCity(@Param("city")String city);
    @Query("SELECT u FROM User u WHERE u.province = :province")
    public List<User> findUsersByProvince(@Param("province") String province);
}
