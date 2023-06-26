package com.cg.controller.rest;


import com.cg.model.dto.UserDTO;
import com.cg.service.user.IUserService;
import com.cg.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        List<UserDTO> userDTOS = userService.findAllUserDTO();

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }
}
