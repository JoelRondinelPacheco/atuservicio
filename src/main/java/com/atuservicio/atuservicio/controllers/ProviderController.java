/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atuservicio.atuservicio.controllers;

/**
 *
 * @author dario
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/provider")

public class ProviderController {
/*
    @GetMapping("/modify/{id}")
   public String getModify(@PathVariable("id") String id, ModelMap model) {

//         // model.addAttribute("user", userService.findById(id)); // esto deberia retornar un userInfoDTO creo xD.

//       return "user_modify.html";

//   }

  @PostMapping("/modify/{id}")
    public String postModify(@PathVariable("id") String id, String name, String email, MultipartFile image, String address, Long address_number, String city, String province, String country, String postal_code, ModelMap model) {

//         try {
            
//             UserInfoDTO userInfoDTO = userService.getById(id); // cambiar null por: userService.findById(id); 
//             userInfoDTO.setName(name);
//             userInfoDTO.setEmail(email);
//             userInfoDTO.setImage(image);
//             userInfoDTO.setAddress(address);
//             userInfoDTO.setAddress_number(address_number);
//             userInfoDTO.setCity(city);
//             userInfoDTO.setProvince(province);
//             userInfoDTO.setCountry(country);
//             userInfoDTO.setPostal_code(postal_code);
//             userService.edit(userInfoDTO);
//             model.put("exito", "Se actualizó el usuario correctamente");

//         } catch (MyException ex) { //error de compilacion de la exception porque la funcion modifyUser deberia lanzar la MyException(con eso deberia arreglarse).

//             model.put("error", ex.getMessage());

//             return "user_modify.html";
//         }

//         return "/l";
//     }

=======
>>>>>>> developer*/
}
