package com.example.bci_ejercicio_java_bunader_gl.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class UserDTO {

    private String name;
    private String email;
    private String password;
    private List<PhoneDTO> phones;

    private String id;
    private String token;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private boolean isActive;
}
