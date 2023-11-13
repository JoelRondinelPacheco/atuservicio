package com.atuservicio.atuservicio.entities;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.util.Date;

@MappedSuperclass         //superclase: no se mapea a una tabla en BBDD
@Getter
public abstract class Base {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String id;
    
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date createdAt;             //fecha de creación
    
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date updatedAt;             //fecha de actualización
}
