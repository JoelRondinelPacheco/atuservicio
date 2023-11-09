

package com.atuservicio.atuservicio.entities;

import com.atuservicio.atuservicio.enums.StateRequest;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request extends Base{
    
    @ManyToOne
    private User customer;
    
    @ManyToOne
    private Supplier supplier;
    
    @OneToOne
    private Contract contract;
    
    private String description;
    
    @Enumerated(EnumType.STRING)
    private StateRequest state;
    
}
