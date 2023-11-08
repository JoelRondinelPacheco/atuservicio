

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

    @OneToOne
    @JoinColumn(name="imageCard_id", referencedColumnName = "id")
    private Image imageCard;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;  //rubro
    
    @OneToMany(mappedBy = "supplier")
    private List<Contract> contractsAsSupplier;
    
    @OneToMany(mappedBy = "supplier")
    private List<Request> requests_received;

    @OneToMany(mappedBy = "supplier")
    private List<Image> imageGallery;
    
    private String description;
    
    private Double priceHour;
    
    // TODO PREPERSIST IMAGE
}
