/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.categories.CategoryInfoDTO;
import com.atuservicio.atuservicio.dtos.services.EditServiceInfoDTO;
import com.atuservicio.atuservicio.dtos.services.ServiceInfoDTO;
import com.atuservicio.atuservicio.dtos.suppliers.EditSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SaveSupplierDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierInfoDTO;
import com.atuservicio.atuservicio.dtos.suppliers.SupplierPaginatedDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserSearchDTO;
import com.atuservicio.atuservicio.entities.Category;
import com.atuservicio.atuservicio.entities.Image;
import com.atuservicio.atuservicio.entities.Supplier;
import com.atuservicio.atuservicio.entities.User;
import com.atuservicio.atuservicio.enums.Role;
import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.CategoryRepository;
import com.atuservicio.atuservicio.repositories.SupplierRepository;
import com.atuservicio.atuservicio.repositories.UserRepository;
import com.atuservicio.atuservicio.services.interfaces.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryService categoryService;

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
        if (supplierDTO.getImage().isEmpty()) {
            Image img = this.imageService.getById("d4fe09fd-56f6-4b55-a1b9-58671d68f1f1");
            Image imgUser = this.imageService.saveDefaultImage(img);
            supplier.setImage(imgUser);
        } else {
            Image img = this.imageService.save(supplierDTO.getImage());
            supplier.setImage(img);
        }
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setAddress_number(supplierDTO.getAddress_number());
        supplier.setCity(supplierDTO.getCity());
        supplier.setProvince(supplierDTO.getProvince());
        supplier.setCountry(supplierDTO.getCountry());
        supplier.setPostal_code(supplierDTO.getPostal_code());
        Category category = this.categoryRepository.findById(supplierDTO.getCategoryId()).get();
        supplier.setCategory(category);
        supplier.setImageCard(category.getImage());

        Supplier supplierSaved = this.supplierRepository.save(supplier);
        System.out.println(supplierSaved.toString());
        return this.createSupplierInfoDTO(supplierSaved);
    }

    @Override
    public SupplierInfoDTO getById(String id) throws MyException {
        Optional<Supplier> supplierOptional = this.supplierRepository.findById(id);
        if (supplierOptional.isPresent()) {
            System.out.println("Encuentra el supplier y lo retorna");
            return this.createSupplierInfoDTO(supplierOptional.get());
        } else {
            throw new MyException("Supplier no encontrado");
        }

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

    public SupplierPaginatedDTO findPaginated(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Supplier> suppliers = this.supplierRepository.findAll(pageable);
        List<SupplierInfoDTO> suppliersDTO = new ArrayList<>();
        for (Supplier s : suppliers) {
            suppliersDTO.add(this.createSupplierInfoDTO(s));
        }
        return new SupplierPaginatedDTO(suppliersDTO, suppliers.getTotalPages(), suppliers.getTotalElements());
    }

    @Override
    public SupplierInfoDTO edit(EditSupplierDTO supplierDTO) throws MyException {
        System.out.println(supplierDTO.getId());
        Optional<Supplier> supplierOptional = this.supplierRepository.findById(supplierDTO.getId());
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            supplier.setName(supplierDTO.getName());
            if (!supplierDTO.getImage().isEmpty()) {
                System.out.println("si imagen");
                String imageId = supplier.getImage().getId();
                this.imageService.update(supplierDTO.getImage(), imageId);
            }
            System.out.println("NO imagen");
            supplier.setAddress(supplierDTO.getAddress());
            supplier.setAddress_number(supplierDTO.getAddress_number());
            supplier.setCity(supplierDTO.getCity());
            System.out.println(supplier.getCity());
            supplier.setProvince(supplierDTO.getProvince());
            supplier.setCountry(supplierDTO.getCountry());
            supplier.setPostal_code(supplierDTO.getPostal_code());
            if (!supplierDTO.getCategoryId().equals(supplier.getCategory().getId())) {
                System.out.println("Distinta categoria");
                Category category = this.categoryRepository.findById(supplierDTO.getCategoryId()).get();
                supplier.setCategory(category);
            }
            System.out.println("Misma categoria");
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
        Optional<User> userOptional = this.userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            System.out.println("Encontro user");
            User user = userOptional.get();
            Optional<Supplier> supplierOptional = this.supplierRepository.findById(user.getId());
            if (supplierOptional.isPresent()) {
                System.out.println("encontro supp");
                return this.createSupplierInfoDTO(supplierOptional.get());
            }
        }
        throw new MyException("Proveedor no encontrado por email");
    }

    public List<SupplierInfoDTO> getSearchSuppliers(UserSearchDTO userSearch) {
        List<SupplierInfoDTO> userInformation = new ArrayList<>();
        System.out.println(userSearch.getCountry());
        if (userSearch.getCity() == null) {
            userSearch.setCity("");
        }
        if (!userSearch.getCity().isEmpty()) {

            List<Supplier> users = supplierRepository.findSuppliersByCity(userSearch.getCity());
            return userInformation = getListSupplierInfoDTO(users);

        } else if (!userSearch.getProvince().isEmpty()) {

            List<Supplier> users = supplierRepository.findSuppliersByProvince(userSearch.getProvince());
            return userInformation = getListSupplierInfoDTO(users);

        } else if (!userSearch.getCountry().isEmpty()) {

            List<Supplier> users = supplierRepository.findSuppliersByCountry(userSearch.getCountry());
            return userInformation = getListSupplierInfoDTO(users);

        }

        return userInformation;
    }

    private List<SupplierInfoDTO> getListSupplierInfoDTO(List<Supplier> users) {

        List<SupplierInfoDTO> infoSuppliers = new ArrayList<>();

        for (Supplier user : users) {

            System.out.println(user);
            SupplierInfoDTO supplierInfo = createSupplierInfoDTO(user);
            infoSuppliers.add(supplierInfo);

        }

        return infoSuppliers;
    }

    public ServiceInfoDTO getServiceInfo(String id) throws MyException{
        Optional<Supplier> supplierOptional = this.supplierRepository.findByEmailSupplier(id);
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            return this.createServiceInfoDTO(supplier);
        }
        throw new MyException("Supplier no encontrado");
    }

    //TODO COMPLETE IMAGE EDITION
    public ServiceInfoDTO editServiceInfo(EditServiceInfoDTO service) throws MyException {
        Optional<Supplier> supplierOptional = this.supplierRepository.findByEmailSupplier(service.getEmail());
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            supplier.setDescription(service.getDescription());
            supplier.setPriceHour(service.getPriceHour());

            List<Image> images = new ArrayList<>();
            for (MultipartFile img : service.getImages()) {
                if (!img.isEmpty()) {
                    Image image = this.imageService.save(img, supplier);
                    images.add(image);
                }
            }

            for (String imageId : service.getDeletedImages()) {
                try {
                    this.imageService.delete(imageId);
                } catch (MyException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            if (!service.getCard().isEmpty()) {
                Image img = this.imageService.save(service.getCard());
                supplier.setImageCard(img);
            }
            supplier.setImageGallery(images);
            Supplier supplierSaved = this.supplierRepository.save(supplier);
            return this.createServiceInfoDTO(supplierSaved);
        }
        throw new MyException("Supplier no encontrado");
    }
    public SupplierInfoDTO createSupplierInfoDTO(Supplier supplier) {
        return new SupplierInfoDTO(
                supplier.getName(),
                supplier.getEmail(),
                supplier.getRole(),
                supplier.getImage().getId(),
                supplier.getAddress(),
                supplier.getAddress_number(),
                supplier.getCity(),
                supplier.getProvince(),
                supplier.getCountry(),
                supplier.getPostal_code(),
                supplier.getId(),
                supplier.getCategory(),
                supplier.getActive(),
                supplier.getImageCard().getId()

        );
    }

    private ServiceInfoDTO createServiceInfoDTO(Supplier supplier) throws MyException {
        List<String> images = new ArrayList<>();
        List<Image> imgs = supplier.getImageGallery();
        System.out.println(imgs.size());
        for (Image img : imgs) {
            images.add(img.getId());
        }
        CategoryInfoDTO category = this.categoryService.getById(supplier.getCategory().getId());
        return new ServiceInfoDTO(supplier.getName(), supplier.getEmail(), supplier.getImageCard().getId(), category, supplier.getDescription(), supplier.getPriceHour(), images);
    }



}

