package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.Request;
import com.atuservicio.atuservicio.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, String> {

    List<Request> findBySupplierId(String id);

    List<Request> findByCustomerId(String id);

}
