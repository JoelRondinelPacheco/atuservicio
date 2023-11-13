

package com.atuservicio.atuservicio.services.interfaces;

import com.atuservicio.atuservicio.dtos.contracts.ContractInfoDTO;
import com.atuservicio.atuservicio.dtos.contracts.SaveContractDTO;
import com.atuservicio.atuservicio.entities.Contract;
import com.atuservicio.atuservicio.exceptions.MyException;
import java.util.List;

public interface IContractService {
    
    ContractInfoDTO save(SaveContractDTO contractDTO) throws MyException;
    List<ContractInfoDTO> getAllContracts();
    ContractInfoDTO getById(String id) throws MyException;
    List<ContractInfoDTO> getByUserId(String id) throws MyException;
    List<ContractInfoDTO> getBySupplierId(String id) throws MyException;
    ContractInfoDTO acceptSupplier(ContractInfoDTO contractDTO) throws MyException;
    ContractInfoDTO declineClient(String contractId) throws MyException;
    ContractInfoDTO cancelClient(String contractId) throws MyException;
    ContractInfoDTO clientDone(String contractId) throws MyException;
    ContractInfoDTO supplierDone(String contractId) throws MyException;
    Contract getFullContractById(String id) throws MyException;
}
