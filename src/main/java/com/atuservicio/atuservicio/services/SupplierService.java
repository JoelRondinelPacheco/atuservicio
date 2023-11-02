/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.suppliers.EditSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SaveSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.services.interfaces.ISupplierService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author PC
 */
@Service
public class SupplierService implements ISupplierService {

    @Override
    public SupplierInfoDTO save(SaveSupplierDTO supplier) throws MyException {
        return null;
    }

    @Override
    public SupplierInfoDTO getById(String id) throws MyException {
        return null;
    }

    @Override
    public List<SupplierInfoDTO> getAllSuppliers() {
        return null;
    }

    @Override
    public SupplierInfoDTO edit(EditSupplierDTO supplier) throws MyException {
        return null;
    }

    @Override
    public String delete(String id) throws MyException {
        return null;
    }
}
