package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.LoginPassDTO;
import com.atuservicio.atuservicio.dtos.users.*;
import com.atuservicio.atuservicio.entities.Image;
import com.atuservicio.atuservicio.entities.User;
import com.atuservicio.atuservicio.enums.Role;
import com.atuservicio.atuservicio.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageService imageService;

    @Override
    public UserInfoDTO save(SaveUserDTO userDTO) throws MyException {
        if (!userDTO.getPassword().equals(userDTO.getPassword2())) {
            throw new MyException("Las contraseñas no coincide");
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        String pass = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        user.setPassword(pass);

        user.setRole(Role.CLIENT);
        if (userDTO.getImage().isEmpty()) {
            System.out.println("imagen vacia");
            Image img = this.imageService.getById("d4fe09fd-56f6-4b55-a1b9-58671d68f1f1");
            Image imgUser = this.imageService.saveDefaultImage(img);
            user.setImage(imgUser);
        } else {
            Image img = this.imageService.save(userDTO.getImage());
            user.setImage(img);
        }
        user.setAddress(userDTO.getAddress());
        user.setAddress_number(userDTO.getAddress_number());
        user.setCity(userDTO.getCity());
        user.setProvince(userDTO.getProvince());
        user.setCountry(userDTO.getCountry());
        user.setPostal_code(userDTO.getPostal_code());
        User userGuardado = this.userRepository.save(user);
        if (userGuardado == null) {
            throw new MyException("No se puedo crear el usuario");
        }
        UserInfoDTO userinfo = this.createUserInfoDTO(userGuardado);

        return userinfo;
    }

    @Override
    public UserInfoDTO getById(String id) throws MyException {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserInfoDTO userinfo = this.createUserInfoDTO(user);
            return userinfo;
        }
        throw new MyException("Usuario no encontrado");
    }

    @Override
    public List<UserInfoDTO> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        List<UserInfoDTO> userInformation = new ArrayList<>();
        for (User user : users) {
            UserInfoDTO userInfo = this.createUserInfoDTO(user);
            userInformation.add(userInfo);
        }
        return userInformation;
    }

    @Override
    public UserInfoDTO edit(EditUserDTO userDTO) throws MyException {
        Optional<User> userOptional = this.userRepository.findById(userDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDTO.getName());
            if (!userDTO.getImage().isEmpty()) {
                String imageId = user.getImage().getId();
                this.imageService.update(userDTO.getImage(), imageId);
            }
            user.setAddress(userDTO.getAddress());
            user.setAddress_number(userDTO.getAddress_number());
            user.setCity(userDTO.getCity());
            user.setProvince(userDTO.getProvince());
            user.setCountry(userDTO.getCountry());
            user.setPostal_code(userDTO.getPostal_code());
            User userSaved = this.userRepository.save(user);
            return this.createUserInfoDTO(userSaved);

        }
        throw new MyException("Usuario no encontrado");
    }

    @Override
    public String delete(String id) throws MyException {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(false);
            this.userRepository.save(user);
            return "Usuario dado de baja correctamente";
        }
        throw new MyException("Usuario no encontrado");
    }

    @Override
    public List<UserInfoDTO> getSearchUsers(UserSearchDTO userSearch) {
        List<UserInfoDTO> userInformation = new ArrayList<>();
        System.out.println(userSearch.getCountry());
        if (userSearch.getCity() == null) {
            userSearch.setCity("");
        }
        if (!userSearch.getCity().isEmpty()) {

            List<User> users = userRepository.findUsersByCity(userSearch.getCity());
            return userInformation = getListUserInfoDTO(users);

        } else if (!userSearch.getProvince().isEmpty()) {

            List<User> users = userRepository.findUsersByProvince(userSearch.getProvince());
            return userInformation = getListUserInfoDTO(users);

        } else if (!userSearch.getCountry().isEmpty()) {

            List<User> users = userRepository.findUsersByCountry(userSearch.getCountry());
            return userInformation = getListUserInfoDTO(users);

        }

        return userInformation;
    }

    @Override
    public UserInfoDTO getSearchEmailUser(String email) throws MyException {
        Optional<User> userOptional = this.userRepository.findByEmail(email);

        if (userOptional.isPresent()) {

            UserInfoDTO userInfo = this.createUserInfoDTO(userOptional.get());

            return userInfo;

        } else {

            throw new MyException("el email no se encontró");
        }
    }

    @Override
    public String activate(String id) throws MyException {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(true);
            this.userRepository.save(user);
            return "Usuario dado de baja correctamente";
        }
        throw new MyException("Usuario no encontrado");
    }
    @Override
    public UserPaginatedDTO findPaginated(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<User> users = this.userRepository.findAll(pageable);
        List<UserInfoDTO> usersInfo = new ArrayList<>();
        for (User user : users) {
            usersInfo.add(this.createUserInfoDTO(user));
        }
        return new UserPaginatedDTO(usersInfo, users.getTotalPages(), users.getTotalElements());
    }
/*
    @Override
    public UserPaginatedDTO findPaginated(int pageNumber, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<User> users = this.userRepository.findAll(pageable);
        List<UserInfoDTO> usersInfo = new ArrayList<>();
        for (User user : users) {
            usersInfo.add(this.createUserInfoDTO(user));
        }
        return new UserPaginatedDTO(usersInfo, users.getTotalPages(), users.getTotalElements());
    }
*/
    private UserInfoDTO createUserInfoDTO(User user) {
        UserInfoDTO userinfo = new UserInfoDTO(
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getImage().getId(),
                user.getAddress(),
                user.getAddress_number(),
                user.getCity(),
                user.getProvince(),
                user.getCountry(),
                user.getPostal_code(),
                user.getId(),
                user.getActive());
        return userinfo;
    }

    public List<UserInfoDTO> getListUserInfoDTO(List<User> users) {

        List<UserInfoDTO> userInformation = new ArrayList<>();
        for (User user : users) {
            System.out.println(user);
            UserInfoDTO userInfo = this.createUserInfoDTO(user);
            userInformation.add(userInfo);
        }
        return userInformation;
    }

}
