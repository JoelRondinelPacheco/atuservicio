

package com.atuservicio.atuservicio.entities;

import com.atuservicio.atuservicio.enums.State;

import javax.persistence.*;

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
public class Request extends Base{
    
    @ManyToOne
    @JoinColumn(name = "id")
    private User customer;
    
    @ManyToOne
    @JoinColumn(name = "id")
    private Supplier supplier;
    
    private String description;
    
    @Enumerated(EnumType.STRING)
    private State state;
    //TODO FINA DATE ?
    
}
