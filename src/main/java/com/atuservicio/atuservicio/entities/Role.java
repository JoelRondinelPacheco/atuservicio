package com.atuservicio.atuservicio.entities;

import javax.naming.Name;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role extends Base {
    
    @Column(unique = true)    //los valores del campo 'role' en la tabla 'roles' deben ser únicos
    private String role;
    
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)   //Relación 'uno a muchos' -->  un rol puede ser asignado a muchos usuarios
    private List<User> users;
    
    /*
    El mappedBy = "role" es necesario si deseas establecer una relación 
    bidireccional entre 'Role' y 'User' y deseas que 'User' sea el 
    propietario de la relación.
    En una relación bidireccional JPA, un lado debe ser el propietario y el 
    otro debe ser el inverso. El lado propietario es el que tiene la 
    clave foránea en la base de datos. En este caso, 'User' es el propietario 
    de la relación porque tiene la clave foránea 'role_id' que apunta a Role.
    Por ende, el mappedBy = "role" en 'Role' indica que la relación @OneToMany 
    con 'User' está mapeada por la propiedad 'role' en la clase 'User'.
    
    El atributo 'cascade' define las operaciones en cascada.
    'CascadeType.ALL' significa que todas las operaciones de persistencia 
    (guardar, actualizar, eliminar, etc.) que se realicen en Role se propagarán 
    también a los User asociados. Por ejemplo, si eliminas un Role, todos los 
    User asociados a ese Role también se eliminarán.
    */
    
}
