package com.atuservicio.atuservicio.controllers;

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
            //Crear la solicitud DTO
            SaveContractDTO requestDTO = new SaveContractDTO(customerDTO, supplierDTO, description);
            //Persistir en BBDD
            ContractInfoDTO r = this.contractService.save(requestDTO);

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
                ContractInfoDTO contractCanceled = this.contractService.cancelClient(contractId);
                model.addAttribute("contract", contractCanceled);
                return "comments_canceled.html";
            } catch (MyException ex) {
                return null;
            }
    }
    //El PROVEEDOR ACEPTA LA SOLICITUD DEL CLIENTE
    @GetMapping("/supplier/accept/{idRequest}")
    public String requestAccepted(@PathVariable("idRequest") String idRequest,@RequestParam Double estimatedTime, ModelMap model) {
        System.out.println("tiempo estimado" + estimatedTime);
        try {
            //Recupero la solicitud en la base de datos mediante su id
            ContractInfoDTO requestDTO = this.contractService.getById(idRequest);
            requestDTO.setEstimatedTime(estimatedTime);
            System.out.println("Tiempo seteado" + requestDTO.getEstimatedTime());
            //La solicitud se envía a la capa de servicios para cambiar su estado a APROBADO y generar el contrato (CONSULTAR)
            ContractInfoDTO r = this.contractService.acceptSupplier(requestDTO);

            //Retorna la lista actualizada de solicitudes de clientes
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
            //Recupero la solicitud en la base de datos mediante su id
            ContractInfoDTO requestDTO = this.contractService.getById(contractId);
            //La solicitud se envía a la capa de servicios para cambiar su estado a RECHAZADO
            ContractInfoDTO r = this.contractService.declineSupplier(requestDTO);

            //Retorna la lista actualizada de solicitudes de clientes
            return "redirect:/contract/list/customers";

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "index.html";
        }
    }

    // DESPUES DE SER ACEPTADO POR EL PROVEEDOR, EL CLIENTE SI ESTA DE ACUERDO CON EL TRABAJO
    @GetMapping("/client/accept/{contractId}")
    public String clientAccept(@PathVariable String contractId, ModelMap model) {
        try {
            ContractInfoDTO contractAccepted = this.contractService.clientDone(contractId);
           model.addAttribute("contract", contractAccepted);
           return "comments_accept";
        } catch (MyException ex) {
            return null;
        }
    }
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

    // DESPUES DE SER ACEPTADO POR EL PROVEEDOR, EL CLIENTE NO ESTA DE ACUERDO CON EL TRABAJO
    @GetMapping("/client/refused/{contractId}")
    public String clientRefused(@PathVariable String contractId, ModelMap model) {
            try {
                ContractInfoDTO contractAccepted = this.contractService.declineClient(contractId);
                model.addAttribute("contract", contractAccepted);
                return "comments_refused";
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

    // DESPUES DEL RECHAZO DEL CLIENTE, EL SUPPLIER PODRIA RESPONDER EL COMENTARIO (RECLAMO)
    @GetMapping("/replica/{contractId}")
    public String responseSupplier(@PathVariable String contractId) {
        // Comentario que le dejo el cliente
        return null;
    }


    // DESPUES DE SER ACEPTADO POR EL PROVEEDOR, EL CLIENTE ESTA DE ACUERDO CON EL TRABAJO BIEN HECHO

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

    //LISTAR TODAS LAS SOLICITUDES GENERADAS EN LA APLICACIÓN
    @GetMapping("/list")
    public String requestListAll(ModelMap model) {

        List<ContractInfoDTO> contracts = contractService.getAllContracts();

        model.addAttribute("contracts", contracts);

        return "request_list_all.html";
    }
}
