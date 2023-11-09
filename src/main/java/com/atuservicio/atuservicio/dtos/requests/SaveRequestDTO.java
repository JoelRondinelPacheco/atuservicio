

package com.atuservicio.atuservicio.dtos.requests;

import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveRequestDTO {
    
    private UserInfoDTO customer;
    private SupplierInfoDTO supplier;
    private String description;
    
}
