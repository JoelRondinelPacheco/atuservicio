package com.atuservicio.atuservicio.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)    //crea dos tablas separadas ('users' y 'suppliers')
public class User extends Base {
    
    private String name;
    
    @Column(unique = true)       //los valores del campo 'email' en la tabla 'users' deben ser únicos
    private String email;
    
    private String password;
    
    private Boolean active;
    
    @ManyToOne                      //Relación 'muchos a uno' --> muchos usuarios pueden tener un mismo rol
    @JoinColumn(name = "role_id")   //el campo 'role_id' de la tabla 'users' almacenará la clave foránea referenciando al id de la tabla 'roles'
    private Role role;
    
    private String image;
    
    private String address;
    
    private Long address_number;
    
    private String city;
    
    private String province;
    
    private String country;
    
    private String postal_code;
    
    @OneToMany(mappedBy = "customer")
    private List<Contract> contracts;
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)   //Relación 'uno a muchos' --> un usuario puede escribi muchos comentarios
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)  //Relación 'uno a muchos' --> un usuario puede recibir muchos comentarios
    private List<Comment> comments_received;
    
    /*
    cascade = CascadeType.ALL --> al eliminar un 'User', automáticamente se 
    eliminan todos los 'Comments' asociados.
    */
}