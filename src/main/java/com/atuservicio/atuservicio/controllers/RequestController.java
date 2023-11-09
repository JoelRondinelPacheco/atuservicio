package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.requests.RequestInfoDTO;
import com.atuservicio.atuservicio.dtos.requests.SaveRequestDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.interfaces.IRequestService;
import com.atuservicio.atuservicio.services.interfaces.ISupplierService;
import com.atuservicio.atuservicio.services.interfaces.IUserService;
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
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private IUserService userService;
    
    @Autowired
    private ISupplierService supplierService;
    
    @Autowired
    private IRequestService requestService;
    
    @GetMapping("/request")
    public String contact () {
        return "request_prueba.html";
    }
    
    @PostMapping("/send/{idSupplier}")
    public String requestSend(@PathVariable("idSupplier") String idSupplier,
            @RequestParam String description, ModelMap model) {

        try {
            //Recupero los detalles del usuario logueado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserInfoDTO customerDTO = userService.getSearchEmailUser(auth.getName());
            //Recupero los detalles del proveedor mediante su id
            SupplierInfoDTO supplierDTO = supplierService.getById(idSupplier);
            //Crear lo solicitud DTO
            SaveRequestDTO requestDTO = new SaveRequestDTO(customerDTO, supplierDTO, description);
            //Persistir en BBDD
            RequestInfoDTO r = this.requestService.save(requestDTO);
            
            model.put("exito", "Solicitud enviada al proveedor exitosamente");
            return "index.html";
            
        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "index.html";
        }

    }
}
