package com.atuservicio.atuservicio.controllers;

import com.atuservicio.atuservicio.dtos.requests.RequestInfoDTO;
import com.atuservicio.atuservicio.dtos.requests.SaveRequestDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.interfaces.IRequestService;
import com.atuservicio.atuservicio.services.interfaces.ISupplierService;
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
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private IRequestService requestService;

    //EL CLIENTE PRESIONA EL BOTON 'CONTRATAR'
    @GetMapping("/form")  //Agregar el idSupplier
    public String requestForm(ModelMap model) {

        try {
            //Recupero los detalles del usuario cliente logueado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserInfoDTO customerDTO = userService.getSearchEmailUser(auth.getName());
            //Recupero los detalles del proveedor seleccionado mediante su id
            SupplierInfoDTO supplierDTO = supplierService.getById("ee0e8d52-6635-48e7-a6c8-6f212f984cc7");
            //Inyecto ambas entidades en el modelo de la vista html
            model.addAttribute("user", customerDTO);
            model.addAttribute("supplier", supplierDTO);

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

        try {
            //Recupero los detalles del usuario cliente logueado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserInfoDTO customerDTO = userService.getSearchEmailUser(auth.getName());
            //Recupero los detalles del proveedor mediante su id
            SupplierInfoDTO supplierDTO = supplierService.getById(idSupplier);
            //Crear la solicitud DTO
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

    //El PROVEEDOR ACEPTA LA SOLICITUD DEL CLIENTE
    @GetMapping("/{idRequest}")
    public String requestAccepted(@PathVariable("idRequest") String idRequest, ModelMap model) {

        try {
            //Recupero la solicitud en la base de datos mediante su id
            RequestInfoDTO requestDTO = this.requestService.getById(idRequest);
            //La solicitud se envía a la capa de servicios para cambiar su estado a APROBADO y generar el contrato (CONSULTAR)
            RequestInfoDTO r = this.requestService.accept(requestDTO);

            //Retorna la lista actualizada de solicitudes de clientes
            return this.requestsFromCustomers(model);

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "index.html";
        }
    }

    //El PROVEEDOR RECHAZA LA SOLICITUD DEL CLIENTE
    @GetMapping("/{idRequest}")
    public String requestRefused(@PathVariable("idRequest") String idRequest, ModelMap model) {

        try {
            //Recupero la solicitud en la base de datos mediante su id
            RequestInfoDTO requestDTO = this.requestService.getById(idRequest);
            //La solicitud se envía a la capa de servicios para cambiar su estado a RECHAZADO
            RequestInfoDTO r = this.requestService.decline(requestDTO);

            //Retorna la lista actualizada de solicitudes de clientes
            return this.requestsFromCustomers(model);

        } catch (MyException ex) {
            model.put("error", ex.getMessage());
            return "index.html";
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
            List<RequestInfoDTO> requests = requestService.getByUserId(customerDTO.getId());

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
            List<RequestInfoDTO> requests = requestService.getBySupplierId(supplierDTO.getId());

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

        List<RequestInfoDTO> requests = requestService.getAllRequests();

        model.addAttribute("requests", requests);

        return "request_list_all.html";
    }
}
