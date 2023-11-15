

package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.comments.CommentInfoDTO;
import com.atuservicio.atuservicio.dtos.comments.SaveCommentDTO;
import com.atuservicio.atuservicio.dtos.contracts.ContractInfoDTO;
import com.atuservicio.atuservicio.entities.Comment;
import com.atuservicio.atuservicio.entities.Contract;
import com.atuservicio.atuservicio.entities.User;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.CommentRepository;
import com.atuservicio.atuservicio.repositories.ContractRepository;
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
    
    @Autowired
    private ContractRepository contractRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ContractService contractService;
    
    //Comentario del cliente (author) por cancelar la solicitud antes de que
    //el proveedor (receiver) lo apruebe o rechace. (SIN PUNTUACION/SCORE)
    @Override
    public CommentInfoDTO save(SaveCommentDTO commentDTO) throws MyException {
        
        User author;
        User receiver;
        Contract contract;
        //Customer es author
        Optional<User> authorOptional = userRepository.findById(commentDTO.getContract().getCustomer().getId());
        if (authorOptional.isPresent()) {
            author = authorOptional.get();
        } else {
            throw new MyException("Usuario no encontrado");
        }
        //Supplier es receiver
        Optional<User> receiverOptional = userRepository.findById(commentDTO.getContract().getSupplier().getId());
        if (receiverOptional.isPresent()) {
            receiver = receiverOptional.get();
        } else {
            throw new MyException("Usuario no encontrado");
        }
        //Contrato
        Optional<Contract> contractOptional = contractRepository.findById(commentDTO.getContract().getId());
        if (contractOptional.isPresent()) {
            contract = contractOptional.get();
        } else {
            throw new MyException("Contrato no encontrado");
        }
        
        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setReceiver(receiver);
        comment.setContract(contract);
        comment.setContent(commentDTO.getContent());
        //No se setea el score
        
        Comment commentSaved = this.commentRepository.save(comment);
        
        return this.createCommentInfoDTO(commentSaved);
    }
    

    private CommentInfoDTO createCommentInfoDTO(Comment comment) {
        
        CommentInfoDTO commentInfo = new CommentInfoDTO(
                comment.getId(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                this.userService.createUserInfoDTO(comment.getAuthor()),
                this.userService.createUserInfoDTO(comment.getReceiver()),
                this.contractService.createContractInfoDTO(comment.getContract()),
                comment.getContent(),
                comment.getScore(),
                comment.getRatings());  //List<Qualification>
        
        return commentInfo;
    }

}
