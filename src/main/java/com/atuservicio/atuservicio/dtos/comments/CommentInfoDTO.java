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
public class CommentInfoDTO {

    private String id;
    private Date createdAt;
    private Date updatedAt;
    private UserInfoDTO author;
    private UserInfoDTO receiver;
    private ContractInfoDTO contract;
    private String content;
    private Double score;
    private List<Qualification> ratings;
}
