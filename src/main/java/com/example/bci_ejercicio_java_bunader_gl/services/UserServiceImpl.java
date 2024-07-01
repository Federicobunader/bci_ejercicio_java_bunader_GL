package com.example.bci_ejercicio_java_bunader_gl.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.bci_ejercicio_java_bunader_gl.dtos.UserDTO;
import com.example.bci_ejercicio_java_bunader_gl.entities.User;
import com.example.bci_ejercicio_java_bunader_gl.mappers.UserMapper;
import com.example.bci_ejercicio_java_bunader_gl.repositories.UserJPARepository;
import com.example.bci_ejercicio_java_bunader_gl.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Value("${password.regex}")
    private String passwordRegex;

    public ResponseEntity<?> createUser(UserDTO userDTO) {

        String emailRegex = "^[a-zA-Z0-9._%+-]+@dominio\\.cl$";

        if(!userDTO.getEmail().matches(emailRegex)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo no es valido");
        }

        boolean emailAlreadyExists = userRepository.findByMail(userDTO.getEmail()).isPresent();

        if(emailAlreadyExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya esta registrado");
        }

        if(!userDTO.getPassword().matches(passwordRegex)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La contraseña no es valida");
        }


        userDTO.setId(UUID.randomUUID().toString());

        String token = generateToken(userDTO.getId()).getBody().toString();
        userDTO.setToken(token);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));

        User user = userRepository.create(userMapper.userDtoToEntity(userDTO));
        userDTO = userMapper.userEntityToDto(user);

        LocalDate currentDate = LocalDate.now();

        userDTO.setCreated(currentDate);
        userDTO.setModified(currentDate);
        userDTO.setLastLogin(currentDate);

        userDTO.setActive(true);
        return ResponseEntity.ok(userDTO);
    }

    private ResponseEntity<?> generateToken(String userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userId", userId)
                    .sign(algorithm);
            return ResponseEntity.ok(token);
        } catch (JWTCreationException exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("mensaje", "Error al generar el token"));
        }
    }
}
