package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.SaveUserDTO;
import com.atuservicio.atuservicio.dtos.UserInfoDTO;
import com.atuservicio.atuservicio.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService implements IUserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfoDTO save(SaveUserDTO user) {
        User userfinal = new User();
        userfinal.setName(user.getName());
        userfinal.setEmail(user.getEmail());
        //...
        User userGuardado = this.userRepository.save(userfinal);
        UserInfoDTO userinfo = new UserInfoDTO(userGuardado.getId(), userGuardado.getName(), );


        return userinfo;
    }

    @Override
    public UserInfoDTO edit(UserInfoDTO user) {
        return null;
    }

    @Override
    public String delete(String id) {
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
