

package com.atuservicio.atuservicio.entities;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends Base{  //rubro
    
    private String name;
    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
}
