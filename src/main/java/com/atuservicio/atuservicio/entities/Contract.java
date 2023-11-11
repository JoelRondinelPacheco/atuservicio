

package com.atuservicio.atuservicio.entities;

import com.atuservicio.atuservicio.enums.State;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contracts")
public class Contract extends Base{

    Boolean customerDone;
    Boolean supplierDone;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    //TODO FINAL DATE ?
    @Temporal(TemporalType.DATE)
    private Date date;

    private String description;

    @Enumerated(EnumType.STRING)
    private State state;

    @PrePersist
    private void prePersist() {
        this.customerDone = false;
        this.supplierDone = false;
    }
    
}
