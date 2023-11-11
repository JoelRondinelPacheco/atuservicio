package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.contracts.ContractInfoDTO;
import com.atuservicio.atuservicio.dtos.contracts.SaveContractDTO;
import com.atuservicio.atuservicio.dtos.services.ServiceInfoDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.SupplierService;
import com.atuservicio.atuservicio.services.interfaces.IContractService;
import com.atuservicio.atuservicio.services.interfaces.IUserService;
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

    //EL CLIENTE PRESIONA EL BOTON 'CONTRATAR'
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

    //El PROVEEDOR ACEPTA LA SOLICITUD DEL CLIENTE
    @GetMapping("/accept/{idRequest}")
    public String requestAccepted(@PathVariable("idRequest") String idRequest, ModelMap model) {

        try {
            //Recupero la solicitud en la base de datos mediante su id
            ContractInfoDTO requestDTO = this.contractService.getById(idRequest);
            //La solicitud se envía a la capa de servicios para cambiar su estado a APROBADO y generar el contrato (CONSULTAR)
            ContractInfoDTO r = this.contractService.accept(requestDTO);

            //Retorna la lista actualizada de solicitudes de clientes
            return "redirect:/request/list/customers";

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "index.html";
        }
    }

    //El PROVEEDOR RECHAZA LA SOLICITUD DEL CLIENTE
    @GetMapping("/decline/{idRequest}")
    public String requestRefused(@PathVariable("idRequest") String idRequest, ModelMap model) {

        try {
            //Recupero la solicitud en la base de datos mediante su id
            ContractInfoDTO requestDTO = this.contractService.getById(idRequest);
            //La solicitud se envía a la capa de servicios para cambiar su estado a RECHAZADO
            ContractInfoDTO r = this.contractService.decline(requestDTO);

            //Retorna la lista actualizada de solicitudes de clientes
            return "redirect:/request/list/customers";

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "index.html";
        }
    }

    //LISTAR LAS SOLICITUDES A PROVEEDORES GENERADAS POR EL CLIENTE LOGUEADO
    @GetMapping("/list/suppliers")
    public String requestsToSuppliers(ModelMap model) {
            System.out.println("Ingreso");
        try {
            
            //Recupero los detalles del usuario cliente logueado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Email" + auth.getName());
            UserInfoDTO customerDTO = userService.getSearchEmailUser(auth.getName());
            //Recupero la lista de solicitudes que realizó el cliente mediante su id
            System.out.println("Recupero el usuario por email");
            List<ContractInfoDTO> requests = contractService.getByUserId(customerDTO.getId());

            model.addAttribute("requests", requests);

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
            List<ContractInfoDTO> requests = contractService.getBySupplierId(supplierDTO.getId());

            model.addAttribute("requests", requests);

            return "request_from_customer_list.html";

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "request_from_customer_list.html";
        }

    }

    //LISTAR TODAS LAS SOLICITUDES GENERADAS EN LA APLICACIÓN
    @GetMapping("/list")
    public String requestListAll(ModelMap model) {

        List<ContractInfoDTO> requests = contractService.getAllContracts();

        model.addAttribute("requests", requests);

        return "request_list_all.html";
    }
}