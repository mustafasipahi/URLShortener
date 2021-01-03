package com.project.services;

import com.project.model.LoginToken;
import com.project.model.UrlShortener;
import com.project.model.User;
import com.project.repository.LoginTokenRepository;
import com.project.util.Constants;
import com.project.util.LoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class LoginTokenService {

    @Autowired
    private LoginTokenRepository repository;

    public LoginToken save(LoginToken entity) {
        return repository.save(entity);
    }

    public Optional<LoginToken> findById(Integer id) {
        return repository.findById(id);
    }

    public LoginToken findByUUID(String UUID){
        return repository.findByUUID(UUID);
    }

    public Optional<LoginToken> findByUserId(Integer userId){
        return repository.findByUserId(userId);
    }

    public boolean existById(Integer id) {
        return findById(id).isPresent();
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
        return randomUUID().toString();
    }
}
