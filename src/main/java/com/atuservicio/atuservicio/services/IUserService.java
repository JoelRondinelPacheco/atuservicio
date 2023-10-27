package com.atuservicio.atuservicio.services;

import com.atuservicio.atuservicio.dtos.SaveUserDTO;
import com.atuservicio.atuservicio.dtos.UserInfoDTO;

import java.util.List;

public interface IUserService {
    UserInfoDTO save(SaveUserDTO user);
    UserInfoDTO edit(UserInfoDTO user);
    String delete(String id);
    UserInfoDTO getById(String id);
    List<UserInfoDTO> getAllUsers();
}
