

package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.comments.CommentInfoDTO;
import com.atuservicio.atuservicio.dtos.comments.SaveCommentDTO;
import com.atuservicio.atuservicio.entities.User;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.CommentRepository;
import com.atuservicio.atuservicio.repositories.UserRepository;
import com.atuservicio.atuservicio.services.interfaces.ICommentService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements ICommentService{
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private UserRepository userRepository;
    /*
    @Override
    public CommentInfoDTO save(SaveCommentDTO commentDTO) throws MyException {
        
        User author;
        
        Optional<User> authorOptional = userRepository.findById(commentDTO.getContract().getCustomer().getId());
        if (authorOptional.isPresent()) {
            author = authorOptional.get();
        } else {
            throw new MyException("Usuario no encontrado");
        }
        
        return null;
    }
    */

    

}
