

package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.Supplier;
import com.atuservicio.atuservicio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends JpaRepository<Supplier, String>{
    
    @Query("SELECT s FROM suppliers s WHERE s.category_id = :category_id")
    public User findByCategory(@Param("category")String category_id);
}
