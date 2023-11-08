

package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.Request;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, String>{
    
    @Query("SELECT r FROM Request r WHERE r.customer = :userId")
    List<Request> findByUserId(@Param("userId") String userId);
    
    @Query("SELECT r FROM Request r WHERE r.supplier = :supplierId")
    List<Request> findBySupplierId(@Param("supplierId") String supplierId);
    
}
