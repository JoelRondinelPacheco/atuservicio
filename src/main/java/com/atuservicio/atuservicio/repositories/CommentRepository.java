package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findByContractId(String id);

    @Query("SELECT c FROM Comment c WHERE receiver_id = :id")
    List<Comment> findBySupplier(@Param("id") String id);
}

