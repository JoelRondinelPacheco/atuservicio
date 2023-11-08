

package com.atuservicio.atuservicio.entities;

import javax.persistence.Entity;
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
    
    private String content;
    
}
