
package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.Supplier;
import com.atuservicio.atuservicio.entities.User;

import java.util.List;
import java.util.Optional;

import com.atuservicio.atuservicio.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {

    /*
     * La anotación '@Inheritance(strategy = InheritanceType.JOINED)' definida
     * en la clase padre 'User' garantiza que, al realizar una consulta sobre
     * la tabla de la clase hija 'Supplier', JPA realice automáticamente un JOIN
     * entre users y suppliers usando las claves primarias/foráneas. (ver diagrama
     * ERD)
     * En resumen, dado que Supplier hereda de User, tiene acceso a los campos de
     * la tabla User.
     */

    @Query("SELECT s FROM Supplier s WHERE s.email = :email")
    public Optional<Supplier> findByEmailSupplier(@Param("email") String email);

    @Query("SELECT s FROM Supplier s WHERE s.category = :idCategory")
    public List<Supplier> findByCategorySupplier(@Param("idCategory") String idCategory);

    @Query("SELECT s FROM Supplier s WHERE s.city = :city OR s.province = :province OR s.country = :country")
    public List<Supplier> findByCityProvinceCountry(@Param("city") String city,
            @Param("province") String province,

            @Param("country") String country);

    @Query("SELECT u FROM Supplier u WHERE u.city = :city")
    public List<Supplier> findSuppliersByCity(@Param("city") String city);

    @Query("SELECT u FROM Supplier u WHERE u.province = :province")
    public List<Supplier> findSuppliersByProvince(@Param("province") String province);

    @Query("SELECT u FROM Supplier u WHERE u.country = :country")
    public List<Supplier> findSuppliersByCountry(@Param("country") String country);


}
