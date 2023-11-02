

package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    
}
