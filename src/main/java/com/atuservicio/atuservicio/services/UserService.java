package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.SaveUserDTO;
import com.atuservicio.atuservicio.dtos.UserInfoDTO;
import com.atuservicio.atuservicio.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atuservicio.atuservicio.exceptions.MyException;


import java.util.List;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfoDTO save(SaveUserDTO userDTO) {
        /*User userfinal = new User();
        userfinal.setName(userDTO.getName());
        userfinal.setEmail(userDTO.getEmail());
        //...
        User userGuardado = this.userRepository.save(userfinal);
        UserInfoDTO userinfo = new UserInfoDTO(userGuardado.getId(), userGuardado.getName(), );


        return userinfo;*/
        return null;
    }

    @Override
    public UserInfoDTO edit(UserInfoDTO userDTO) {
      /*  Optional<User> userOptional = this.userRepository.findById(user.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDTO.getName());
            user.setEmail(user.getEmail());
            user.setRole(userDTO.getRole());
            user.setImage(userDTO.getImage());

        }*/
        return null;
    }

    @Override
    public String delete(String id) throws MyException {
        return null;
    }

    @Override
    public UserInfoDTO getById(String id) {
        return null;
    }

    @Override
    public List<UserInfoDTO> getAllUsers() {
        return null;
    }

}
