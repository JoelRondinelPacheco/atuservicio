package com.atuservicio.atuservicio.services.interfaces;

import com.atuservicio.atuservicio.dtos.*;
import com.atuservicio.atuservicio.dtos.users.*;
import com.atuservicio.atuservicio.entities.User;
import com.atuservicio.atuservicio.exceptions.MyException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {
    UserInfoDTO save(SaveUserDTO user) throws MyException ;
    UserInfoDTO getById(String id) throws MyException;
    List<UserInfoDTO> getAllUsers();
    UserInfoDTO edit(EditUserDTO user) throws MyException;
    String delete(String id) throws MyException;
    List<UserInfoDTO> getSearchUsers(UserSearchDTO userSearch);
    UserInfoDTO getSearchEmailUser(String userSearch) throws MyException;
    String activate(String id) throws MyException;
    UserPaginatedDTO findPaginated(int pageNumber, int pageSize);

}
