package com.example.bci_ejercicio_java_bunader_gl.controllers;

import com.example.bci_ejercicio_java_bunader_gl.dtos.UserDTO;
import com.example.bci_ejercicio_java_bunader_gl.services.IUserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Api(value = "User Management System")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create a new user")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserDTO userDTO) {
        ResponseEntity<?> response = userService.createUser(userDTO);
        Map<String, Object> responseBody = new HashMap<>();
        if (response.getStatusCode().isError()) {
            responseBody.put("mensaje", response.getBody());
        } else {
            UserDTO userDTOResponse = (UserDTO) response.getBody();

            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());
            responseBody = om.convertValue(userDTOResponse, new TypeReference<Map<String, Object>>() {});
        }
        return new ResponseEntity<>(responseBody, response.getStatusCode());
    }
}
