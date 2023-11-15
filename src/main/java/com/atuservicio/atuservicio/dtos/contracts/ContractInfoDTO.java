

package com.atuservicio.atuservicio.dtos.contracts;

import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.enums.State;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractInfoDTO {
    
    private String id;
    private Date createdAt;
    private Date updatedAt;
    private String description;
    private State state;
    private UserInfoDTO customer;
    private SupplierInfoDTO supplier;
    private boolean hasComment;
    private Double estimatedTime;
}
