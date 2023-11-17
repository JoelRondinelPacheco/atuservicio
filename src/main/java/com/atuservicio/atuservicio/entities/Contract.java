

package com.atuservicio.atuservicio.entities;

import com.atuservicio.atuservicio.enums.State;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contracts")
public class Contract extends Base{

    Boolean customerDone;
    Boolean supplierDone;
    Boolean rejectedBudget;
    Integer cantidadComentarios = 0;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "contract")
    private List<Comment> comments;


    //TODO FINAL DATE ?
    @Temporal(TemporalType.DATE)
    private Date date;

    private String description;

    @Enumerated(EnumType.STRING)
    private State state;

    private Double estimatedTime;

    @PrePersist
    private void prePersist() {
        this.customerDone = false;
        this.supplierDone = false;
        this.rejectedBudget = false;
    }
    
}
