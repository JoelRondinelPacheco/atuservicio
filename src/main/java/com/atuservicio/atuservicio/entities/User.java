package com.atuservicio.atuservicio.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Base {
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    Boolean active;
    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> role;
    @OneToMany(mappedBy = "emisor", cascade = CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany(mappedBy = "receptor", cascade = CascadeType.ALL)
    private List<Comment> comentarios_recibidos;
}