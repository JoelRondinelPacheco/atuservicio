package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.Contract;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {

    List<Contract> findBySupplierId(String id);

    List<Contract> findByCustomerId(String id);

}
