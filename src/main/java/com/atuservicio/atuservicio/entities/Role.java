package com.atuservicio.atuservicio.entities;

import javax.naming.Name;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends Base {
    @Column(unique = true)
    private String role;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users;
}
