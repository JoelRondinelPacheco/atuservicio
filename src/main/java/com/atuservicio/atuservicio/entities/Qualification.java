

package com.atuservicio.atuservicio.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ratings")
public class Qualification extends Base{
    
    private String predefined;
    
    @ManyToMany(mappedBy = "ratings")
    private List<Comment> comments;
    
}
