package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.SaveUserDTO;
import com.atuservicio.atuservicio.dtos.UserInfoDTO;
import com.atuservicio.atuservicio.exceptions.MyException;

import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class UserService implements IUserService{
    @Override
    public UserInfoDTO save(SaveUserDTO user) throws MyException{
        return null;
    }

    @Override
    public UserInfoDTO edit(UserInfoDTO user) throws MyException {
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
