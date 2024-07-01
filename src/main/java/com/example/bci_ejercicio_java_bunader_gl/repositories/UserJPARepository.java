package com.example.bci_ejercicio_java_bunader_gl.repositories;

import com.example.bci_ejercicio_java_bunader_gl.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJPARepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String mail);
}
