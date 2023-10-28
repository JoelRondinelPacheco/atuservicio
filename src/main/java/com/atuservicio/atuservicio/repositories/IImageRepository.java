package com.atuservicio.atuservicio.repositories;

import com.atuservicio.atuservicio.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageRepository extends JpaRepository<Image, String> {
}
