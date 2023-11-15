package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.contracts.ContractInfoDTO;
import com.atuservicio.atuservicio.dtos.contracts.SaveContractDTO;
import com.atuservicio.atuservicio.entities.Contract;
import com.atuservicio.atuservicio.entities.Supplier;
import com.atuservicio.atuservicio.entities.User;
import com.atuservicio.atuservicio.enums.State;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.ContractRepository;
import com.atuservicio.atuservicio.repositories.SupplierRepository;
import com.atuservicio.atuservicio.repositories.UserRepository;
import com.atuservicio.atuservicio.services.interfaces.IContractService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService implements IContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SupplierService supplierService;
    
    //EL CLIENTE ENVÍA LA SOLICITUD AL PROVEEDOR
    @Override
    public ContractInfoDTO save(SaveContractDTO contractDTO) throws MyException {

        User customer;
        Supplier supplier;

        Optional<User> userOptional = userRepository.findById(contractDTO.getCustomer().getId());
        if (userOptional.isPresent()) {
            customer = userOptional.get();
        } else {
            throw new MyException("Usuario no encontrado");
        }

        Optional<Supplier> supplierOptional = supplierRepository.findById(contractDTO.getSupplier().getId());
        if (supplierOptional.isPresent()) {
            supplier = supplierOptional.get();
        } else {
            throw new MyException("Proveedor no encontrado");
        }

        Contract contract = new Contract();
        contract.setCustomer(customer);
        contract.setSupplier(supplier);
        contract.setDescription(contractDTO.getDescription());
        contract.setState(State.PENDING_APPROVAL);


        Contract contractSaved = this.contractRepository.save(contract);

        return this.createContractInfoDTO(contractSaved);  //linea 212
    }

    @Override
    public List<ContractInfoDTO> getAllContracts() {

        List<Contract> contracts = this.contractRepository.findAll();

        List<ContractInfoDTO> requestsInfo = new ArrayList<>();
        for (Contract r : contracts) {
            ContractInfoDTO rInfo = this.createContractInfoDTO(r);
            requestsInfo.add(rInfo);
        }
        return requestsInfo;
    }

    @Override
    public ContractInfoDTO getById(String id) throws MyException {

        Optional<Contract> requestOptional = this.contractRepository.findById(id);
        if (requestOptional.isPresent()) {
            Contract contract = requestOptional.get();
            ContractInfoDTO requestInfo = this.createContractInfoDTO(contract);
            return requestInfo;
        }
        throw new MyException("Solicitud no encontrada");
    }

    @Override
    public List<ContractInfoDTO> getByUserId(String id) throws MyException {
        List<Contract> contracts = this.contractRepository.findByCustomerId(id);
        List<ContractInfoDTO> requestsInfo = new ArrayList<>();
        for (Contract r : contracts) {
            ContractInfoDTO rInfo = this.createContractInfoDTO(r);
            requestsInfo.add(rInfo);
        }
        return requestsInfo;
    }

    @Override
    public List<ContractInfoDTO> getBySupplierId(String id) throws MyException {

        List<Contract> contracts = this.contractRepository.findBySupplierId(id);

        List<ContractInfoDTO> requestsInfo = new ArrayList<>();
        for (Contract r : contracts) {
            ContractInfoDTO rInfo = this.createContractInfoDTO(r);
            requestsInfo.add(rInfo);
        }
        return requestsInfo;
    }
    
    //El PROVEEDOR ACEPTA LA SOLICITUD DEL CLIENTE ENVIANDO UN TIEMPO ESTIMADO (COTIZA)
    @Override
    public ContractInfoDTO acceptSupplier(ContractInfoDTO contractDTO) throws MyException {
        
        Optional<Contract> contractOptional = this.contractRepository.findById(contractDTO.getId());
        
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            if (contract.getState().equals(State.PENDING_APPROVAL)) {
                contract.setState(State.APPROVED_SUPPLIER);
                contract.setEstimatedTime(contractDTO.getEstimatedTime());
            }
            Contract contractSaved = this.contractRepository.save(contract);
            return this.createContractInfoDTO(contractSaved);  //linea 212
        }
        throw new MyException("Solicitud no encontrada");
    }
    
    //El PROVEEDOR RECHAZA LA SOLICITUD DEL CLIENTE
    @Override
    public ContractInfoDTO declineSupplier(ContractInfoDTO contractDTO) throws MyException {
        
        Optional<Contract> contractOptional = this.contractRepository.findById(contractDTO.getId());
        
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            if (contract.getState().equals(State.PENDING_APPROVAL)) {
                contract.setState(State.REFUSED_SUPPLIER);
            }
            Contract contractSaved = this.contractRepository.save(contract);
            return this.createContractInfoDTO(contractSaved);
        }
        throw new MyException("Solicitud no encontrada");
    }
    
    //EL CLIENTE ACEPTA LA COTIZACION DEL PROVEEDOR
    @Override
    public ContractInfoDTO approveBudgetClient(ContractInfoDTO contractDTO) throws MyException {
        
        Optional<Contract> contractOptional = this.contractRepository.findById(contractDTO.getId());
        
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            if (contract.getState().equals(State.APPROVED_SUPPLIER)) {
                contract.setState(State.PENDING_COMPLETION);
            }
            Contract contractSaved = this.contractRepository.save(contract);
            return this.createContractInfoDTO(contractSaved);
        }
        throw new MyException("Solicitud no encontrada");
    }
    
    //1. EL CLIENTE RECHAZA EL SERVICIO COTIZADO POR EL PROVEEDOR
    //2. EL CLIENTE QUEDA DISCONFORME CON EL TRABAJO REALIZADO POR EL PROVEEDOR
    @Override
    public ContractInfoDTO declineClient(String contractId) throws MyException {

        Optional<Contract> contractOptional = this.contractRepository.findById(contractId);

        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            if (contract.getState().equals(State.APPROVED_SUPPLIER) || (contract.getState().equals(State.PENDING_COMPLETION) )) {
                contract.setState(State.REFUSED_CLIENT);
                //En este  punto puede emitir comentario
            }
            Contract contractSaved = this.contractRepository.save(contract);
            return this.createContractInfoDTO(contractSaved);
        }
        throw new MyException("Solicitud no encontrada");
    }
    
    //EL CLIENTE CANCELA LA SOLICITUD QUE HIZO ANTES DE QUE EL PRROVEEDOR LA ACEPTE O RECHACE
    @Override
    public ContractInfoDTO cancelClient(String contractId) throws MyException {

        Optional<Contract> contractOptional = this.contractRepository.findById(contractId);

        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            if (contract.getState().equals(State.PENDING_APPROVAL)) {
                contract.setState(State.CANCELED_CLIENT);
                //En este  punto puede emitir comentario
            }
            Contract contractSaved = this.contractRepository.save(contract);
            return this.createContractInfoDTO(contractSaved); //linea 212
        }
        throw new MyException("Solicitud no encontrada");
    }
    
    //EL CLIENTE CONSIDERA FINALIZADO EL TRABAJO DEL PROVEEDOR Y QUEDA CONFORME
    @Override
    public ContractInfoDTO clientDone(String contractId) throws MyException {
        Contract contract = this.getContractById(contractId);
        contract.setCustomerDone(true);
        if (contract.getState().equals(State.PENDING_COMPLETION)) {
            contract.setState(State.DONE_CLIENT);
        }
        Contract contractDone = this.contractRepository.save(contract);
        return createContractInfoDTO(contractDone);
    }

    @Override
    public ContractInfoDTO supplierDone(String contractId) throws MyException {
        Contract contract = this.getContractById(contractId);
        contract.setSupplierDone(true);
        if (contract.getState().equals(State.PENDING_COMPLETION)) {
            contract.setState(State.DONE_SUPPLIER);
        }
        Contract contractDone = this.contractRepository.save(contract);
        return createContractInfoDTO(contractDone);
    }

    @Override
    public Contract getFullContractById(String id) throws MyException {
        Optional<Contract> contract = this.contractRepository.findById(id);
        if (contract.isPresent()) {
            return contract.get();
        }
        throw new MyException("Contrato no encontrado");
    }

    public ContractInfoDTO createContractInfoDTO(Contract contract) {
        Boolean hasComments;

        if (contract.getComments() == null || contract.getComments().size() == 0) {
            hasComments = false;
        } else {
            hasComments = true;
        }
        ContractInfoDTO contractinfo = new ContractInfoDTO(
                contract.getId(),
                contract.getCreatedAt(),
                contract.getUpdatedAt(),
                contract.getDescription(),
                contract.getState(),
                this.userService.createUserInfoDTO(contract.getCustomer()),
                this.supplierService.createSupplierInfoDTO(contract.getSupplier()),
                hasComments,
                contract.getEstimatedTime());

        return contractinfo;
    }

    private Contract getContractById(String id) throws MyException {
        Optional<Contract> contractOptional = this.contractRepository.findById(id);
        if (contractOptional.isPresent()) {
            return contractOptional.get();
        }
        throw new MyException("Contrato no encontrado");
    }

    

}
