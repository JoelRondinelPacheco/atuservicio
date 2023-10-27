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
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String image;
    private String address;
    private Long address_number;
    private String city;
    private String province;
    private String country;
    private String postal_code;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Comment> comments_received;
}