package com.project.repository;

import com.project.model.LoginToken;
import com.project.model.UrlShortener;
import com.project.model.User;
import com.project.repository.baserepo.MyEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginTokenRepository extends MyEntityRepository<LoginToken> {

    public LoginToken findByUUID(String UUID);
    public LoginToken findByUserId(Integer userId);
}
