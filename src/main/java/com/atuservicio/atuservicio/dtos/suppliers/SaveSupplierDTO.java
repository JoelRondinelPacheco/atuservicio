package com.atuservicio.atuservicio.dtos.suppliers;

import com.atuservicio.atuservicio.dtos.users.SaveUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveSupplierDTO extends SaveUserDTO {
    private String categoryId;
    public SaveSupplierDTO(String name, String email, String password, String password2, String categoryId) {
        super(name, email);
        this.categoryId = categoryId;
    }
}
