

package com.atuservicio.atuservicio.entities;

import java.util.Date;
import javax.persistence.*;

import com.atuservicio.atuservicio.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contracts")
public class Contract extends Base{
    
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

}
