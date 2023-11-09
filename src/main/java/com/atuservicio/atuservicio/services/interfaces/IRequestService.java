

package com.atuservicio.atuservicio.services.interfaces;

import com.atuservicio.atuservicio.dtos.requests.RequestInfoDTO;
import com.atuservicio.atuservicio.dtos.requests.SaveRequestDTO;
import com.atuservicio.atuservicio.exceptions.MyException;
import java.util.List;

public interface IRequestService {
    
    RequestInfoDTO save(SaveRequestDTO requestDTO) throws MyException;
    List<RequestInfoDTO> getAllRequests();
    RequestInfoDTO getById(String id) throws MyException;
    List<RequestInfoDTO> getByUserId(String id) throws MyException;
    List<RequestInfoDTO> getBySupplierId(String id) throws MyException;
    RequestInfoDTO accept(RequestInfoDTO requestDTO) throws MyException;
    RequestInfoDTO decline(RequestInfoDTO requestDTO) throws MyException;
}
