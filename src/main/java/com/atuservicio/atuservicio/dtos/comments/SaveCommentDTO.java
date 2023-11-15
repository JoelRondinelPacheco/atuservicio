

package com.atuservicio.atuservicio.dtos.comments;

import com.atuservicio.atuservicio.dtos.contracts.ContractInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.entities.Qualification;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveCommentDTO {
    
    private ContractInfoDTO contract;
    private String content;
    private Double score;
    
    public SaveCommentDTO(ContractInfoDTO contract, String content) {
        this.contract = contract;
        this.content = content;
    }

}
