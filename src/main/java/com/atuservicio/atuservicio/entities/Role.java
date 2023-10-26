package com.atuservicio.atuservicio.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Role extends Base {
    @Id
    String id;
    @ManyToOne
    String role;
}
