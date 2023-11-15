package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.Category;
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

        @Query("SELECT s FROM Supplier s WHERE s.category.id = :idCategory")
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

        @Query("SELECT s FROM Supplier s "
                        + "WHERE (:country = '' OR :country IS NULL OR s.country = :country) "
                        + "AND (:province = '' OR :province IS NULL OR s.province = :province) "
                        + "AND (:city = '' OR :city IS NULL OR s.city = :city)"
                        + "AND (:category = '' OR :category IS NULL OR s.category = :category)")
        public List<Supplier> findByCountryAndProvinceAndCityAndCategory(@Param("country") String country,
                        @Param("province") String province, @Param("city") String city,
                        @Param("category") String category);

        @Query("SELECT s FROM Supplier s "
                        + "WHERE (:category = '' OR :category IS NULL OR s.category = :category)"
                        + "AND (:province = '' OR :province IS NULL OR s.province = :province)")
        public Page<Supplier> findByCategoryAndProvince(@Param("province") String province,
                        @Param("category") String category, Pageable pageable);

        public Page<Supplier> findAll(Pageable pageable);
        @Query("SELECT s FROM Supplier s WHERE s.active = :active")
        Page<Supplier> findAllActive(Pageable pageable, @Param("active") Boolean active);

        public Supplier findByEmail(String email);
}
