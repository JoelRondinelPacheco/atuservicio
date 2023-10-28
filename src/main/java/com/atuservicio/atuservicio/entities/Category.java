

package com.atuservicio.atuservicio.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends Base{  //rubro
    
    private String name;
}
