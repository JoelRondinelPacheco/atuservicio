package com.atuservicio.atuservicio.services.interfaces;

import com.atuservicio.atuservicio.dtos.*;
import com.atuservicio.atuservicio.dtos.users.EditUserDTO;
import com.atuservicio.atuservicio.dtos.users.SaveUserDTO;
import com.atuservicio.atuservicio.dtos.users.UserInfoDTO;
import com.atuservicio.atuservicio.dtos.users.UserSearchDTO;
import com.atuservicio.atuservicio.entities.User;
import com.atuservicio.atuservicio.exceptions.MyException;

import java.util.List;

public interface IUserService {
    UserInfoDTO save(SaveUserDTO user) throws MyException ;
    UserInfoDTO getById(String id) throws MyException;
    List<UserInfoDTO> getAllUsers();
    UserInfoDTO edit(EditUserDTO user) throws MyException;
    String delete(String id) throws MyException;
    List<UserInfoDTO> getSearchUsers(UserSearchDTO userSearch);
    UserInfoDTO getSearchEmailUser(LoginPassDTO userSearch) throws MyException;


}
