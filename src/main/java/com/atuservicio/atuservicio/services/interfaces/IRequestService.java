

package com.atuservicio.atuservicio.services.interfaces;

import com.atuservicio.atuservicio.dtos.requests.RequestInfoDTO;
import com.atuservicio.atuservicio.dtos.requests.SaveRequestDTO;
import com.atuservicio.atuservicio.exceptions.MyException;

public interface IRequestService {
    
    RequestInfoDTO save(SaveRequestDTO requestDTO) throws MyException;
}
