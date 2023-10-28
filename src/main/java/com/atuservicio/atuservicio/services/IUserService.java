package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.EditUserDTO;
import com.atuservicio.atuservicio.dtos.SaveUserDTO;
import com.atuservicio.atuservicio.dtos.UserInfoDTO;
import com.atuservicio.atuservicio.exceptions.MyException;

import java.util.List;

public interface IUserService {
    UserInfoDTO save(SaveUserDTO user) throws MyException ;
<<<<<<< HEAD
    UserInfoDTO edit(UserInfoDTO user) throws MyException;
    String delete(String id) throws MyException;
    UserInfoDTO getById(String id);
=======
    UserInfoDTO edit(EditUserDTO user) throws MyException;
    String delete(String id) throws MyException;
    UserInfoDTO getById(String id) throws MyException;
>>>>>>> developer
    List<UserInfoDTO> getAllUsers();
}
