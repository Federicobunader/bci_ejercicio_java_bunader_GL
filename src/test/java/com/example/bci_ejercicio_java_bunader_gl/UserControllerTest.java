package com.example.bci_ejercicio_java_bunader_gl;

import com.example.bci_ejercicio_java_bunader_gl.controllers.UserController;
import com.example.bci_ejercicio_java_bunader_gl.dtos.UserDTO;
import com.example.bci_ejercicio_java_bunader_gl.dtos.PhoneDTO;
import com.example.bci_ejercicio_java_bunader_gl.services.IUserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private IUserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserSuccessfully() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Juan Rodriguez");
        userDTO.setEmail("juan@rodriguez.org");
        userDTO.setPassword("hunter2");

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber("1234567");
        phoneDTO.setCitycode("1");
        phoneDTO.setContrycode("57");

        userDTO.setPhones(Collections.singletonList(phoneDTO));

        UserDTO createdUserDTO = new UserDTO();
        createdUserDTO.setName("Juan Rodriguez");
        createdUserDTO.setEmail("juan@rodriguez.org");
        createdUserDTO.setPassword("hunter2");
        createdUserDTO.setPhones(Collections.singletonList(phoneDTO));

        when(userService.createUser(userDTO)).thenAnswer(invocation -> ResponseEntity.ok(createdUserDTO));

        ResponseEntity<Map<String, Object>> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdUserDTO.getName(), response.getBody().get("name"));
        assertEquals(createdUserDTO.getEmail(), response.getBody().get("email"));
        assertEquals(createdUserDTO.getPassword(), response.getBody().get("password"));

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> expectedPhones = mapper.convertValue(createdUserDTO.getPhones(), new TypeReference<List<Map<String, Object>>>() {
        });
        assertEquals(expectedPhones, response.getBody().get("phones"));
    }

    @Test
    public void testCreateUserWithInvalidEmail() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Juan Rodriguez");
        userDTO.setEmail("juan@invalidemail");
        userDTO.setPassword("hunter2");

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber("1234567");
        phoneDTO.setCitycode("1");
        phoneDTO.setContrycode("57");

        userDTO.setPhones(Collections.singletonList(phoneDTO));

        when(userService.createUser(userDTO)).thenAnswer(invocation -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo no es valido"));

        ResponseEntity<Map<String, Object>> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        assertEquals("El correo no es valido", response.getBody().get("mensaje"));
    }

    @Test
    public void testCreateUserWithExistingEmail() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Juan Rodriguez");
        userDTO.setEmail("juan@rodriguez.org");
        userDTO.setPassword("hunter2");

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber("1234567");
        phoneDTO.setCitycode("1");
        phoneDTO.setContrycode("57");

        userDTO.setPhones(Collections.singletonList(phoneDTO));
        when(userService.createUser(userDTO)).thenAnswer(invocation -> ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya esta registrado"));

        ResponseEntity<Map<String, Object>> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        assertEquals("El correo ya esta registrado", response.getBody().get("mensaje"));
    }

    @Test
    public void testCreateUserWithInvalidPassword() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Juan Rodriguez");
        userDTO.setEmail("juan@rodriguez.org");
        userDTO.setPassword("invalidpassword"); // Contraseña no válida

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber("1234567");
        phoneDTO.setCitycode("1");
        phoneDTO.setContrycode("57");

        userDTO.setPhones(Collections.singletonList(phoneDTO));
        when(userService.createUser(userDTO)).thenAnswer(invocation -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La contraseña no es valida"));

        ResponseEntity<Map<String, Object>> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        assertEquals("La contraseña no es valida", response.getBody().get("mensaje"));
    }
}