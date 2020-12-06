package com.project.services;

import com.project.model.LoginToken;
import com.project.model.User;
import com.project.repository.LoginTokenRepository;
import com.project.services.baseservice.MyEntityService;
import com.project.util.Constants;
import com.project.util.LoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginTokenService implements MyEntityService<LoginToken> {

    @Autowired
    private LoginTokenRepository repository;

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public LoginToken save(LoginToken entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<LoginToken> findById(Integer id) {
        return repository.findById(id);
    }

    public LoginToken findByUUID(String UUID){
        return repository.findByUUID(UUID);
    }

    @Override
    public List<LoginToken> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existById(Integer id) {
        final Optional<LoginToken> fromDb = findById(id);
        if (fromDb.isPresent()){
            return true;
        }
        return false;
    }

    public LoginToken saveAnonymousLoginToken(){
        return saveNewLoginToken(LoginType.ANONYMOUS,
                Constants.ANONYMOUS_LOGIN_TOKEN_EXPIRATION_DURATION,
                null);
    }

    public LoginToken saveUserLoginToken(User user){
        return saveNewLoginToken(LoginType.LOGGED_IN,
                Constants.LOGGED_IN_LOGIN_TOKEN_EXPIRATION_DURATION,
                user);
    }

    private LoginToken saveNewLoginToken(LoginType type, Long expration, User user){
        LoginToken loginToken = new LoginToken();
        loginToken.setType(type);
        loginToken.setExpiration_duration(expration);
        loginToken.setUser(user);
        loginToken.setUUID(newUUID());
        return save(loginToken);
    }

    private String newUUID(){
        String UUID = java.util.UUID.randomUUID().toString();
        return UUID;
    }
}
