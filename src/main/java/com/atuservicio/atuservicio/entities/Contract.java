

package com.atuservicio.atuservicio.entities;

import com.atuservicio.atuservicio.enums.State;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "contracts")
public class Contract extends Base{
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;
    
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    private State state;
    
}
