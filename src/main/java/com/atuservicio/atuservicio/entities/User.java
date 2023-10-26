package com.atuservicio.atuservicio.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.List;

@Entity
public class User extends Base {
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    Boolean active;
    @ManyToOne
    private List<Role> role;
    @OneToMany(mappedBy = "emisor", cascade = CascadeType.ALL)
    private List<Comment> comments;
}