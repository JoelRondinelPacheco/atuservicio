package com.atuservicio.atuservicio.services.interfaces;

import com.atuservicio.atuservicio.dtos.suppliers.EditSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SaveSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.exceptions.MyException;

import java.util.List;

public interface ISupplierService {
    SupplierInfoDTO save(SaveSupplierDTO supplier) throws MyException;
    SupplierInfoDTO getById(String id) throws MyException;
    List<SupplierInfoDTO> getAllSuppliers();
    SupplierInfoDTO edit(EditSupplierDTO supplier) throws MyException;
    String delete(String id) throws MyException;
}
