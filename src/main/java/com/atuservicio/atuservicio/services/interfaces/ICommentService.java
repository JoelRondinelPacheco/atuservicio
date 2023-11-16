package com.atuservicio.atuservicio.services.interfaces;

import com.atuservicio.atuservicio.dtos.comments.CommentInfoDTO;
import com.atuservicio.atuservicio.dtos.comments.SaveCommentDTO;
import com.atuservicio.atuservicio.exceptions.MyException;
import java.util.List;

public interface ICommentService {
   
    CommentInfoDTO save(SaveCommentDTO commentDTO) throws MyException;
   
}
