package com.project.services;

import com.project.model.LoginToken;
import com.project.repository.UserRepository;
import com.project.model.User;
import com.project.services.baseservice.MyEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements MyEntityService<User> {

    @Autowired
    private UserRepository repository;

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public User save(User entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existById(Integer id) {
        final Optional<User> fromDb = findById(id);
        if (fromDb.isPresent()){
            return true;
        }
        return false;
    }
}
