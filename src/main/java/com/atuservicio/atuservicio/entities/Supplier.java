

package com.atuservicio.atuservicio.entities;

import java.util.List;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "suppliers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Supplier extends User {

    @OneToOne
    @JoinColumn(name="imageCard_id", referencedColumnName = "id")
    private Image imageCard;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;  //rubro
    
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Contract> contractsAsSupplier;


    @OneToMany(mappedBy = "supplier")
    private List<Image> imageGallery;
    
    private String description;
    
    private Double priceHour;
    
    @PrePersist
    private void prePersist() {
        this.priceHour = 0D;
        this.description = "";
    }
}
