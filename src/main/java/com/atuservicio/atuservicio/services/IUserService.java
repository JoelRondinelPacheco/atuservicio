package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.*;
import com.atuservicio.atuservicio.dtos.users.EditUserDTO;
import com.atuservicio.atuservicio.dtos.users.SaveUserDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserSearchDTO;
import com.atuservicio.atuservicio.exceptions.MyException;

import java.util.List;

public interface IUserService {
    UserInfoDTO save(SaveUserDTO user) throws MyException ;
    UserInfoDTO edit(EditUserDTO user) throws MyException;
    String delete(String id) throws MyException;
    UserInfoDTO getById(String id) throws MyException;
    List<UserInfoDTO> getAllUsers();
    List<UserInfoDTO> getSearchUsers(UserSearchDTO userSearch);
    public UserInfoDTO getSearchEmailUser(LoginPassDTO userSearch) throws MyException;
}
