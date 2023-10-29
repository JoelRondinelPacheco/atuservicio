package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.EditUserDTO;
import com.atuservicio.atuservicio.dtos.LoginPassDTO;
import com.atuservicio.atuservicio.dtos.SaveUserDTO;
import com.atuservicio.atuservicio.dtos.UserInfoDTO;
import com.atuservicio.atuservicio.dtos.UserSearchDTO;
import com.atuservicio.atuservicio.entities.Image;
import com.atuservicio.atuservicio.entities.Role;
import com.atuservicio.atuservicio.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atuservicio.atuservicio.exceptions.MyException;
import com.atuservicio.atuservicio.repositories.UserRepository;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class UserService implements IUserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired ImageService imageService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserInfoDTO save(SaveUserDTO userDTO) throws MyException {
        if (!userDTO.getPassword().equals(userDTO.getPassword2())) {
            throw new MyException("Las contraseñas no coincide");
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        Role role = this.roleService.findByRole("CLIENT");
        user.setRole(role);
        Image img = this.imageService.save(userDTO.getImage());
        user.setImage(img);
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
    public UserInfoDTO edit(EditUserDTO userDTO) throws MyException {
        //TODO CHECKEAR SE LA IMAGEN ES LA MISMA PARA NO HACER EL PROCESO DE GUARDA LA IMAGEN OTRA VEZ
        Optional<User> userOptional = this.userRepository.findById(userDTO.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            String imageId = user.getImage().getId();
            Image image = this.imageService.update(userDTO.getImage(), imageId);
            //TODO REVISAR SI NO FUNCIONA
            user.setImage(image);
            user.setAddress(userDTO.getAddress());
            user.setAddress_number(userDTO.getAddress_number());
            user.setCity(userDTO.getCity());
            user.setProvince(userDTO.getProvince());
            user.setCountry(userDTO.getCountry());
            user.setPostal_code(userDTO.getPostal_code());

            return this.createUserInfoDTO(user);

        }
        throw new MyException("Usuario no encontrado");
    }

    @Override
    public String delete(String id) throws MyException {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            this.userRepository.deleteById(id);
            return "Usuario eliminado correctamente";
        }
        throw new MyException("Usuario no encontrado");
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

    public List<UserInfoDTO> getSearchUsers(UserSearchDTO userSearch) {
        List<User> users = this.userRepository.findByCityProvinceCountry(userSearch.getCity(), userSearch.getProvince(), userSearch.getCountry());
        List<UserInfoDTO> userInformation = new ArrayList<>();
        for (User user : users) {
            UserInfoDTO userInfo = this.createUserInfoDTO(user);
            userInformation.add(userInfo);
        }
        return userInformation;
    }
    
    public UserInfoDTO getSearchEmailUser(LoginPassDTO userSearch) throws MyException{
        
        User user = this.userRepository.findByEmailUser(userSearch.getEmail());
        
        if (user != null){
        
            UserInfoDTO userInfo = this.createUserInfoDTO(user);
        
            return userInfo;
            
        } else {
            
            throw new MyException("el email no se encontró");
        }
    }
    
    private UserInfoDTO createUserInfoDTO(User user) {
        UserInfoDTO userinfo = new UserInfoDTO(
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getImage(),
                user.getAddress(),
                user.getAddress_number(),
                user.getCity(),
                user.getProvince(),
                user.getCountry(),
                user.getPostal_code(),
                user.getId()
        );
        return userinfo;
    }
}
