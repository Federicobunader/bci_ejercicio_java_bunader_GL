package com.example.bci_ejercicio_java_bunader_gl.repositories;

import com.example.bci_ejercicio_java_bunader_gl.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository{

    @Autowired
    UserJPARepository repository;

    protected JpaRepository<User, Long> getRepository() {
        return repository;
    }

    public Optional<User> findByMail(String mail) { return repository.findByEmailIgnoreCase(mail);}

    public User create(User user) {
        return getRepository().save(user);
    }
}
