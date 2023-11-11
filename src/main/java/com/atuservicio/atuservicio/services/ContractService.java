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

    @Override
    public ContractInfoDTO save(SaveContractDTO requestDTO) throws MyException {

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

        Contract contract = new Contract();
        contract.setCustomer(customer);
        contract.setSupplier(supplier);
        contract.setDescription(requestDTO.getDescription());
        contract.setState(State.PENDING_APPROVAL);


        Contract contractSaved = this.contractRepository.save(contract);

        return this.createContractInfoDTO(contractSaved);
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
        System.out.println("Ingreso al servicio de busquda por idUsuario " + id);
        List<Contract> contracts = this.contractRepository.findByCustomerId(id);
        System.out.println("Recuperé las solicitudes");
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
    
    @Override
    public ContractInfoDTO accept(ContractInfoDTO requestDTO) throws MyException {
        
        Optional<Contract> requestOptional = this.contractRepository.findById(requestDTO.getId());
        
        if (requestOptional.isPresent()) {
            Contract contract = requestOptional.get();
            if (contract.getState().equals(State.PENDING_APPROVAL)) {
                contract.setState(State.APPROVED);
            }
            Contract contractSaved = this.contractRepository.save(contract);
            return this.createContractInfoDTO(contractSaved);
        }
        throw new MyException("Solicitud no encontrada");
    }
    
    @Override
    public ContractInfoDTO decline(ContractInfoDTO requestDTO) throws MyException {
        
        Optional<Contract> contractOptional = this.contractRepository.findById(requestDTO.getId());
        
        if (contractOptional.isPresent()) {
            Contract contract = contractOptional.get();
            if (contract.getState().equals(State.PENDING_APPROVAL)) {
                contract.setState(State.REFUSED);
            }
            Contract contractSaved = this.contractRepository.save(contract);
            return this.createContractInfoDTO(contractSaved);
        }
        throw new MyException("Solicitud no encontrada");
    }

    @Override
    public ContractInfoDTO clientDone(String contractId) throws MyException {
        Contract contract = this.getContractById(contractId);
        contract.setCustomerDone(true);
        if (contract.getSupplierDone()) {
            contract.setState(State.DONE);
        }
        Contract contractDone = this.contractRepository.save(contract);
        return createContractInfoDTO(contractDone);
    }

    @Override
    public ContractInfoDTO supplierDone(String contractId) throws MyException {
        Contract contract = this.getContractById(contractId);
        contract.setSupplierDone(true);
        if (contract.getSupplierDone()) {
            contract.setState(State.DONE);
        }
        Contract contractDone = this.contractRepository.save(contract);
        return createContractInfoDTO(contractDone);
    }

    private ContractInfoDTO createContractInfoDTO(Contract contract) {
        ContractInfoDTO requestinfo = new ContractInfoDTO(
                contract.getId(),
                contract.getCreatedAt(),
                contract.getDescription(),
                contract.getState(),
                this.userService.createUserInfoDTO(contract.getCustomer()),
                this.supplierService.createSupplierInfoDTO(contract.getSupplier()));
        return requestinfo;
    }

    private Contract getContractById(String id) throws MyException {
        Optional<Contract> contractOptional = this.contractRepository.findById(id);
        if (contractOptional.isPresent()) {
            return contractOptional.get();
        }
        throw new MyException("Contrato no encontrado");
    }

}
