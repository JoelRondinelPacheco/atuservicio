package com.atuservicio.atuservicio.entities;

import javax.naming.Name;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends Base {
    @Column(unique = true)
    private String role;
    @ManyToMany(mappedBy = "role")
    private List<User> users;
}
