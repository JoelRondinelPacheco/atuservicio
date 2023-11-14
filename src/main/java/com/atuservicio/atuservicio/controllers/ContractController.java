package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.comments.CommentInfoDTO;
import com.atuservicio.atuservicio.dtos.comments.SaveCommentDTO;
import com.atuservicio.atuservicio.dtos.contracts.ContractInfoDTO;
import com.atuservicio.atuservicio.dtos.contracts.SaveContractDTO;
import com.atuservicio.atuservicio.dtos.services.ServiceInfoDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.entities.Comment;
import com.atuservicio.atuservicio.entities.Contract;
import com.atuservicio.atuservicio.entities.User;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.CommentRepository;
import com.atuservicio.atuservicio.services.CommentService;
import com.atuservicio.atuservicio.services.SupplierService;
import com.atuservicio.atuservicio.services.interfaces.IContractService;
import com.atuservicio.atuservicio.services.interfaces.IUserService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private IUserService userService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    private IContractService contractService;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private CommentService commentService;

    //EL CLIENTE PRESIONA EL BOTON 'CONTACTAR'
    @GetMapping("/form/{id}")
    public String requestForm(@PathVariable("id") String id,ModelMap model) {

        try {
            //Recupero los detalles del usuario cliente logueado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserInfoDTO customerDTO = userService.getSearchEmailUser(auth.getName());
            //Recupero los detalles del proveedor seleccionado mediante su id
            SupplierInfoDTO supplierDTO = supplierService.getById(id);
            ServiceInfoDTO service = supplierService.getServiceInfo(supplierDTO.getEmail());

            //Inyecto ambas entidades en el modelo de la vista html
            model.addAttribute("user", customerDTO);
            model.addAttribute("supplier", supplierDTO);
            model.addAttribute("service", service);

            return "request_form.html";

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "request_form.html";
        }
    }

    //EL CLIENTE ENVÍA LA SOLICITUD AL PROVEEDOR
    @PostMapping("/send/{idSupplier}")
    public String requestSend(@PathVariable("idSupplier") String idSupplier,
            @RequestParam String description, ModelMap model) {
        System.out.println("Devuelve formulario");
        try {
            //Recupero los detalles del usuario cliente logueado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserInfoDTO customerDTO = userService.getSearchEmailUser(auth.getName());
            //Recupero los detalles del proveedor mediante su id
            SupplierInfoDTO supplierDTO = supplierService.getById(idSupplier);
            //Instanciar el contrato DTO
            SaveContractDTO contractDTO = new SaveContractDTO(customerDTO, supplierDTO, description);
            //El contrato se envía a la capa de servicios para inicializar su estado como PENDING_APPROVAL
            ContractInfoDTO c = this.contractService.save(contractDTO);

            model.put("exito", "Solicitud enviada al proveedor exitosamente");
            return "index.html";

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "index.html";
        }
    }
    
    // EL CLIENTE PUEDE CANCELAR LA SOLICITUD DEL TRABAJO ANTES QUE EL PRROVEEDOR LA ACEPTE 
    @GetMapping("/client/canceled/{contractId}")
    public String clientCanceled(@PathVariable String contractId, ModelMap model) {
            try {
                //El contrato se envía a la capa de servicios para cambiar su estado a CANCELED_CLIENT
                ContractInfoDTO contractCanceled = this.contractService.cancelClient(contractId);
                
                model.addAttribute("contract", contractCanceled);
                return "comments_canceled.html";
                
            } catch (MyException ex) {
                return null;
            }
    }
    
    //El PROVEEDOR ACEPTA LA SOLICITUD DEL CLIENTE ENVIANDO UN TIEMPO ESTIMADO (COTIZA)
    @GetMapping("/supplier/accept/{idContract}")
    public String requestAccepted(@PathVariable("idContract") String idContract, @RequestParam Double estimatedTime, ModelMap model) {
        System.out.println("tiempo estimado" + estimatedTime);
        try {
            //Recupero el contrato en la base de datos mediante su id
            ContractInfoDTO contractDTO = this.contractService.getById(idContract);
            contractDTO.setEstimatedTime(estimatedTime);
            System.out.println("Tiempo seteado" + contractDTO.getEstimatedTime());
            //El contrato se envía a la capa de servicios para cambiar su estado a APPROVED_SUPPLIER
            ContractInfoDTO c = this.contractService.acceptSupplier(contractDTO);

            model.put("acepto", "Aceptaste la solicitu del contrato");
            return "redirect:/contract/list/customers";

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "index.html";
        }
    }

    //El PROVEEDOR RECHAZA LA SOLICITUD DEL CLIENTE
    @GetMapping("/supplier/decline/{contractId}")
    public String requestRefused(@PathVariable("contractId") String contractId, ModelMap model) {

        try {
            //Recupero el contrato en la base de datos mediante su id
            ContractInfoDTO contractDTO = this.contractService.getById(contractId);
            //El contrato se envía a la capa de servicios para cambiar su estado a REFUSED_SUPPLIER
            ContractInfoDTO c = this.contractService.declineSupplier(contractDTO);

            return "redirect:/contract/list/customers";

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "index.html";
        }
    }
    
    //EL CLIENTE ACEPTA LA COTIZACION DEL PROVEEDOR
    @GetMapping("/client/approveBudget/{contractId}")
    public String approveBudget(@PathVariable("contractId") String contractId, ModelMap model){
        try {
            //Recupero el contrato en la base de datos mediante su id
            ContractInfoDTO contractDTO = this.contractService.getById(contractId);
            //El contrato se envía a la capa de servicios para cambiar su estado a PENDING_COMPLETION
            ContractInfoDTO c = this.contractService.approveBudgetClient(contractDTO);
            
            return "redirect:/contract/list/suppliers";
            
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "index.html";
        }
    }
    
    //1. EL CLIENTE RECHAZA EL SERVICIO COTIZADO POR EL PROVEEDOR
    //2. EL CLIENTE QUEDA DISCONFORME CON EL TRABAJO REALIZADO POR EL PROVEEDOR
    @GetMapping("/client/refused/{contractId}")
    public String clientRefused(@PathVariable String contractId, ModelMap model) {
            try {
                //El contrato se envía a la capa de servicios para cambiar su estado a REFUSED_CLIENT
                ContractInfoDTO contractDTO = this.contractService.declineClient(contractId);
                
                model.addAttribute("contract", contractDTO);
                return "comments_refused";
                
            } catch (MyException ex) {
                model.put("error", ex.getMessage());
                return "index.html";
            }
    }
    
    //EL CLIENTE CONSIDERA FINALIZADO EL TRABAJO DEL PROVEEDOR Y QUEDA CONFORME
    @GetMapping("/client/accept/{contractId}")
    public String clientAccept(@PathVariable String contractId, ModelMap model) {
        try {
            //El contrato se envía a la capa de servicios para cambiar su estado a DONE_CLIENT
            ContractInfoDTO contractDTO = this.contractService.clientDone(contractId);
            
            model.addAttribute("contract", contractDTO);
            return "comments_accept";
            
        } catch (MyException ex) {
            return null;
        }
    }
    
    //EL PROVEEDOR CONSIDERA FINALIZADO SU TRABAJO
    @GetMapping("/supplier/done/{contractId}")
    public String supplierAccept(@PathVariable String contractId, ModelMap model) {
        try {
            //El contrato se envía a la capa de servicios para cambiar su estado a DONE_SUPPLIER
            ContractInfoDTO contractDTO = this.contractService.supplierDone(contractId);
            
            model.addAttribute("contract", contractDTO);
            return "comments_supplier";  //no existe el html
            
        } catch (MyException ex) {
            return null;
        }
    }
    
    //----------------------------------COMENTARIOS-----------------------------
    
    //ENDPOINT PARA ENVIAR EL COMENTARIO OK
    @PostMapping("/comment/accept/{contractId}")
    public String clientCommentOk(@PathVariable String contractId, @RequestParam String content, @RequestParam Double score, ModelMap model) {
        try {
            // TODO PASAR LA LOGICA NECESARIO AL CONTRACT O COMMENT SERVICE
            this.contractService.clientDone(contractId);
            
            Contract contract = this.contractService.getFullContractById(contractId);
            User author = this.userService.getUserById(contract.getCustomer().getId());
            User receiver = this.userService.getUserById(contract.getSupplier().getId());
            
            Comment comment = new Comment();
            comment.setAuthor(author);
            comment.setReceiver(receiver);
            comment.setContent(content);
            comment.setScore(score);
            comment.setContract(contract);
            Comment commentSaved = this.commentRepository.save(comment);
            //List<Comment> comments = this.commentRepository.findAll();
            
            model.addAttribute("comment", commentSaved);
            
            return this.requestsToSuppliers(model);
        } catch (MyException ex) {
            return null;
        }
    }

    //POST CLIENT REFUSED
    @PostMapping("/comment/refused/{contractId}")
    public String clientCommentRefused(@PathVariable String contractId, @RequestParam String content, @RequestParam Double score, ModelMap model) {
        try {
            // TODO PASAR LA LOGICA NECESARIO AL CONTRACT O COMMENT SERVICE
            this.contractService.declineClient(contractId);
            
            Contract contract = this.contractService.getFullContractById(contractId);
            User author = this.userService.getUserById(contract.getCustomer().getId());
            User receiver = this.userService.getUserById(contract.getSupplier().getId());
            
            Comment comment = new Comment();
            comment.setAuthor(author);
            comment.setReceiver(receiver);
            comment.setContent(content);
            comment.setScore(score);
            comment.setContract(contract);
            Comment commentSaved = this.commentRepository.save(comment);
            
            model.addAttribute("comment", commentSaved);
            return this.requestsToSuppliers(model);
            
        } catch (MyException ex) {
            return null;
        }
    }
    
    /*
    @PostMapping("/comment/canceled/{contractId}")
    public String clientCommentCanceled(@PathVariable String contractId, @RequestParam String content, ModelMap model) {
        try {
            //Recupero el contrato de la base de datos
            ContractInfoDTO contractDTO = this.contractService.getById(contractId);
            //Instancio un nuevo comentario y lo persisto en la base de datos
            SaveCommentDTO commentDTO = new SaveCommentDTO(contractDTO, content);
            CommentInfoDTO c = this.commentService.save(commentDTO);
            
        } catch (MyException ex) {
        }
        
    }
    */
    
    
    
    

    // DESPUES DEL RECHAZO DEL CLIENTE, EL SUPPLIER PODRIA RESPONDER EL COMENTARIO (RECLAMO)
    @GetMapping("/replica/{contractId}")
    public String responseSupplier(@PathVariable String contractId) {
        // Comentario que le dejo el cliente
        return null;
    }

    //COMENTARIOS
    @GetMapping("/comments/{contractId}")
    public String commentsList(@PathVariable String contractId, ModelMap model) {
        //Recupero los comentarios específicos al contrato mediante su id
        List<Comment> comments = this.commentRepository.findByContractId(contractId);
        model.addAttribute("comments", comments);
        return "comments_list";
    }
    @GetMapping("/comments/supplier/{contractId}")
    public String commentsSupplierList(@PathVariable String contractId, ModelMap model) {
        List<Comment> comments = this.commentRepository.findByContractId(contractId);
        model.addAttribute("comments", comments);
        model.put("contractId", contractId);
        return "comments_supplier_list";
    }
    @PostMapping("/comments/supplier/response/{contractId}")
    public String commentsSupplierResponse(@PathVariable String contractId, @RequestParam String content, @RequestParam Double score, ModelMap model) {
        try {
            Contract contract = this.contractService.getFullContractById(contractId);
            User receiver = this.userService.getUserById(contract.getCustomer().getId());
            User author = this.userService.getUserById(contract.getSupplier().getId());
            Comment comment = new Comment();
            comment.setAuthor(author);
            comment.setReceiver(receiver);
            comment.setContent(content);
            comment.setScore(score);
            comment.setContract(contract);
            this.commentRepository.save(comment);
            return this.requestsFromCustomers(model);
        } catch (MyException ex) {
            return null;
        }
    }
    
    
    
    
    
    
    
    //LISTAR LAS SOLICITUDES A PROVEEDORES GENERADAS POR EL CLIENTE LOGUEADO
    @GetMapping("/list/suppliers")
    public String requestsToSuppliers(ModelMap model) {
            
        try {
            //Recupero los detalles del usuario cliente logueado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserInfoDTO customerDTO = userService.getSearchEmailUser(auth.getName());
            //Recupero la lista de solicitudes que realizó el cliente mediante su id
            List<ContractInfoDTO> contracts = contractService.getByUserId(customerDTO.getId());
            List<Comment> comments = this.commentRepository.findAll();
            
            model.addAttribute("contracts", contracts);

            return "request_to_supplier_list.html";

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "request_to_supplier_list.html";
        }
    }

    //LISTAR LAS SOLICITUDES DE CLIENTES RECIBIDAS POR EL PROVEEDOR
    @GetMapping("/list/customers")
    public String requestsFromCustomers(ModelMap model) {

        try {
            //Recupero los detalles del usuario proveedor logueado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            SupplierInfoDTO supplierDTO = supplierService.getByEmail(auth.getName());
            //Recupero la lista de solicitudes que recibió el proveedor mediante su id
            List<ContractInfoDTO> contracts = contractService.getBySupplierId(supplierDTO.getId());

            model.addAttribute("contracts", contracts);

            return "request_from_customer_list.html";

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "request_from_customer_list.html";
        }

    }
    
    //LISTAR TODAS LAS SOLICITUDES GENERADAS EN LA APLICACIÓN
    @GetMapping("/list")
    public String requestListAll(ModelMap model) {

        List<ContractInfoDTO> contracts = contractService.getAllContracts();

        model.addAttribute("contracts", contracts);

        return "request_list_all.html";
    }
}
