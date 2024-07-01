package com.example.bci_ejercicio_java_bunader_gl.services;

import com.example.bci_ejercicio_java_bunader_gl.dtos.UserDTO;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    ResponseEntity<?> createUser(UserDTO userDTO);
}
