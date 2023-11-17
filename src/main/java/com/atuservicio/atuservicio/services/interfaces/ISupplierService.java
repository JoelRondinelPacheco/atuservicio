package com.atuservicio.atuservicio.services.interfaces;

import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.dtos.suppliers.EditSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SaveSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierPaginatedDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.exceptions.MyException;

import java.util.List;

public interface ISupplierService {
    SupplierInfoDTO save(SaveSupplierDTO supplier) throws MyException;
    SupplierInfoDTO getById(String id) throws MyException;
    List<SupplierInfoDTO> getAllSuppliers();
    SupplierInfoDTO edit(EditSupplierDTO supplier) throws MyException;
    String delete(String id) throws MyException;
    String activate(String id) throws MyException;
    SupplierInfoDTO getByEmail(String email) throws MyException;

    SupplierInfoDTO convertToSupplier(UserInfoDTO customerDTO, CategoryInfoDTO categoryDTO) throws MyException;
    SupplierPaginatedDTO findPaginated(int pageNumber, int pageSize);

    

}
