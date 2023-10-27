package com.atuservicio.atuservicio.entities;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
@MappedSuperclass
@Getter
public class Base {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String id;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
}
