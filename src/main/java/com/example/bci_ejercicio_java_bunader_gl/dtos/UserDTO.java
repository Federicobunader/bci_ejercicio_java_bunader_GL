package com.example.bci_ejercicio_java_bunader_gl.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate created;
    private LocalDate modified;
    private LocalDate lastLogin;
    private boolean isActive;
}
