

package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.comments.CommentInfoDTO;
import com.atuservicio.atuservicio.dtos.comments.SaveCommentDTO;
import com.atuservicio.atuservicio.dtos.contracts.ContractInfoDTO;
import com.atuservicio.atuservicio.entities.Comment;
import com.atuservicio.atuservicio.entities.Contract;
import com.atuservicio.atuservicio.entities.User;
import com.atuservicio.atuservicio.enums.State;
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
    
    @Override
    public CommentInfoDTO save(SaveCommentDTO commentDTO) throws MyException {
        
        Contract contract = this.getContractById(commentDTO.getContract().getId());
        /*El autor y el receptor depende del estado que adquirió el contrato en
        el instante previo a efectuar el comentario*/
        User author;
        User receiver;
        
        Comment comment = new Comment();
        
        switch (contract.getState()) {
            case CANCELED_CLIENT:
                //Si el cliente cancela: author=customer; receiver=supplier
                author = this.getUserById(commentDTO.getContract().getCustomer().getId());
                receiver = this.getUserById(commentDTO.getContract().getSupplier().getId());
                break;
            case REFUSED_SUPPLIER:
                //Si el proveedor rechaza: author=supplier; receiver=customer
                author = this.getUserById(commentDTO.getContract().getSupplier().getId());
                receiver = this.getUserById(commentDTO.getContract().getCustomer().getId());
                break;
            case REFUSED_CLIENT:
                //Si el cliente está disconforme: author=customer; receiver=supplier
                author = this.getUserById(commentDTO.getContract().getCustomer().getId());
                receiver = this.getUserById(commentDTO.getContract().getSupplier().getId());
                comment.setScore(commentDTO.getScore());
                break;
            case DONE_CLIENT:
                //Si el cliente está conforme: author=customer; receiver=supplier
                author = this.getUserById(commentDTO.getContract().getCustomer().getId());
                receiver = this.getUserById(commentDTO.getContract().getSupplier().getId());
                comment.setScore(commentDTO.getScore());
                break;
            case DONE_SUPPLIER:
                //Si el proveedor da por finalizado el trabajo: author=supplier; receiver=customer
                author = this.getUserById(commentDTO.getContract().getSupplier().getId());
                receiver = this.getUserById(commentDTO.getContract().getCustomer().getId());
                comment.setScore(commentDTO.getScore());
                break;
            default:
                author = null;
                receiver = null;
                break;
        }
        
        comment.setAuthor(author);
        comment.setReceiver(receiver);
        comment.setContract(contract);
        comment.setContent(commentDTO.getContent());
        
        Comment commentSaved = this.commentRepository.save(comment);
        return this.createCommentInfoDTO(commentSaved);
    }
    
    private Contract getContractById(String id) throws MyException {
        Optional<Contract> contractOptional = this.contractRepository.findById(id);
        if (contractOptional.isPresent()) {
            return contractOptional.get();
        }
        throw new MyException("Contrato no encontrado");
    }
    
    private User getUserById(String id) throws MyException {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new MyException("Usuario no encontrado");
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
