package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.requests.RequestInfoDTO;
import com.atuservicio.atuservicio.dtos.requests.SaveRequestDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.entities.Request;
import com.atuservicio.atuservicio.entities.Supplier;
import com.atuservicio.atuservicio.entities.User;
import com.atuservicio.atuservicio.enums.StateRequest;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.RequestRepository;
import com.atuservicio.atuservicio.repositories.SupplierRepository;
import com.atuservicio.atuservicio.repositories.UserRepository;
import com.atuservicio.atuservicio.services.interfaces.IRequestService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService implements IRequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SupplierService supplierService;

    @Override
    public RequestInfoDTO save(SaveRequestDTO requestDTO) throws MyException {

        User customer;
        Supplier supplier;

        Optional<User> userOptional = userRepository.findById(requestDTO.getCustomer().getId());
        if (userOptional.isPresent()) {
            customer = userOptional.get();
        } else {
            throw new MyException("Usuario no encontrado");
        }

        Optional<Supplier> supplierOptional = supplierRepository.findById(requestDTO.getSupplier().getId());
        if (supplierOptional.isPresent()) {
            supplier = supplierOptional.get();
        } else {
            throw new MyException("Proveedor no encontrado");
        }

        Request request = new Request();
        request.setCustomer(customer);
        request.setSupplier(supplier);
        request.setDescription(requestDTO.getDescription());
        request.setState(StateRequest.PENDING);
        request.setContract(null);
        
        Request requestGuardado = this.requestRepository.save(request);
        System.out.println("Request guardado:" + requestGuardado.getDescription());
        
        RequestInfoDTO requestInfo = this.createRequestInfoDTO(request);

        return requestInfo;
    }

    private RequestInfoDTO createRequestInfoDTO(Request request) {
        RequestInfoDTO requestinfo = new RequestInfoDTO(
                request.getId(),
                request.getCreatedAt(),
                request.getDescription(),
                request.getState(),
                request.getContract(),
                this.userService.createUserInfoDTO(request.getCustomer()),
                this.supplierService.createSupplierInfoDTO(request.getSupplier()));
        return requestinfo;
    }

}
