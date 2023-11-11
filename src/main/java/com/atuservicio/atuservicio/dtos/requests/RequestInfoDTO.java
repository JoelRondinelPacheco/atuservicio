

package com.atuservicio.atuservicio.dtos.requests;

import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.entities.Contract;
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
public class RequestInfoDTO {
    
    private String id;
    private Date createdAt;
    private String description;
    private State state;
    private Contract contract;
    private UserInfoDTO customer;
    private SupplierInfoDTO supplier;
    
}
