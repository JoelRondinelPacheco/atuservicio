/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.suppliers.EditSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SaveSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.entities.Category;
import com.atuservicio.atuservicio.entities.Image;
import com.atuservicio.atuservicio.entities.Supplier;
import com.atuservicio.atuservicio.enums.Role;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.CategoryRepository;
import com.atuservicio.atuservicio.repositories.SupplierRepository;
import com.atuservicio.atuservicio.services.interfaces.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author PC
 */
@Service
public class SupplierService implements ISupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public SupplierInfoDTO save(SaveSupplierDTO supplierDTO) throws MyException {
        if (!supplierDTO.getPassword().equals(supplierDTO.getPassword2())) {
            throw new MyException("Las contraseñas no son iguales");
        }
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setEmail(supplierDTO.getEmail());
        String password = new BCryptPasswordEncoder().encode(supplierDTO.getPassword());
        supplier.setPassword(password);
        supplier.setRole(Role.SUPPLIER);
        if (supplierDTO.getImage() != null) {
            Image img = this.imageService.save(supplierDTO.getImage());
            supplier.setImage(img);
        } else {
            Image img = this.imageService.getById("d4fe09fd-56f6-4b55-a1b9-58671d68f1f1");
            Image imgUser = this.imageService.saveDefaultImage(img);
            supplier.setImage(imgUser);
        }
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setAddress_number(supplierDTO.getAddress_number());
        supplier.setCity(supplierDTO.getCity());
        supplier.setProvince(supplierDTO.getProvince());
        supplier.setCountry(supplierDTO.getCountry());
        supplier.setPostal_code(supplierDTO.getPostal_code());
        Category category = this.categoryRepository.findById(supplierDTO.getCategoryId()).get();
        supplier.setCategory(category);

        Supplier supplierSaved = this.supplierRepository.save(supplier);
        return this.createSupplierInfoDTO(supplierSaved);
    }

    @Override
    public SupplierInfoDTO getById(String id) throws MyException {
        Optional<Supplier> supplierOptional = this.supplierRepository.findById(id);
        if (supplierOptional.isPresent()) {
            return this.createSupplierInfoDTO(supplierOptional.get());
        }
        throw new MyException("Supplier no encontrado");
    }

    @Override
    public List<SupplierInfoDTO> getAllSuppliers() {
        List<Supplier> suppliers = this.supplierRepository.findAll();
        List<SupplierInfoDTO> suppliersDTO = new ArrayList<>();
        for (Supplier s : suppliers) {
            suppliersDTO.add(this.createSupplierInfoDTO(s));
        }
        return suppliersDTO;
    }

    @Override
    public SupplierInfoDTO edit(EditSupplierDTO supplierDTO) throws MyException {
        Optional<Supplier> supplierOptional = this.supplierRepository.findById(supplierDTO.getId());
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            supplier.setName(supplierDTO.getName());
            String imageId = supplier.getImage().getId();
            this.imageService.update(supplierDTO.getImage(), imageId);
            supplier.setAddress(supplierDTO.getAddress());
            supplier.setAddress_number(supplierDTO.getAddress_number());
            supplier.setCity(supplierDTO.getCity());
            supplier.setProvince(supplierDTO.getProvince());
            supplier.setCountry(supplierDTO.getCountry());
            supplier.setPostal_code(supplierDTO.getPostal_code());
            Category category = this.categoryRepository.findById(supplierDTO.getCategoryId()).get();
            supplier.setCategory(category);
            Supplier supplierSaved = this.supplierRepository.save(supplier);
            return this.createSupplierInfoDTO(supplierSaved);
        }
        throw new MyException("Supplier no encontrado");
    }

    @Override
    public String delete(String id) throws MyException {
        Optional<Supplier> supplierOptional = this.supplierRepository.findById(id);
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            supplier.setActive(false);
            this.supplierRepository.save(supplier);
            return "Supplier dado de baja correctamente";
        }
        throw new MyException("Supplier no encontrado");
    }

    @Override
    public String activate(String id) throws MyException {
        Optional<Supplier> supplierOptional = this.supplierRepository.findById(id);
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            supplier.setActive(true);
            this.supplierRepository.save(supplier);
            return "Supplier dado de alta correctamente";
        }
        throw new MyException("Supplier no encontrado");
    }
    @Override
    public SupplierInfoDTO getByEmail(String email) throws MyException {
        Optional<Supplier> supplierOptional = this.supplierRepository.findByEmailSupplier(email);
        if (supplierOptional.isPresent()) {
            return this.createSupplierInfoDTO(supplierOptional.get());
        }
        throw new MyException("Supplier no encontrado");
    }

    private SupplierInfoDTO createSupplierInfoDTO(Supplier supplier) {
        return new SupplierInfoDTO(
                supplier.getName(),
                supplier.getEmail(),
                supplier.getRole(),
                supplier.getImage(),
                supplier.getAddress(),
                supplier.getAddress_number(),
                supplier.getCity(),
                supplier.getProvince(),
                supplier.getCountry(),
                supplier.getPostal_code(),
                supplier.getId(),
                supplier.getCategory(),
                supplier.getActive()
        );
    }
}
