

package com.atuservicio.atuservicio.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "suppliers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Supplier extends User {
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;  //rubro
    
    @OneToMany(mappedBy = "supplier")
    private List<Contract> contractsAsSupplier;
}
