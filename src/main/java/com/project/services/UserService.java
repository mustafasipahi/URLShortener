package com.project.services;

import com.project.model.LoginToken;
import com.project.repository.UserRepository;
import com.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService{

    @Autowired
    private UserRepository repository;

    public User save(User entity) {
        return repository.save(entity);
    }

    public Optional<User> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean existById(Integer id) {
        return findById(id).isPresent();
    }
}
