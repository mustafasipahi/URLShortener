package com.project.repository;

import com.project.model.LoginToken;
import com.project.model.User;
import com.project.repository.baserepo.MyEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginTokenRepository extends MyEntityRepository<LoginToken> {

    @Query("SELECT lt FROM LoginToken lt WHERE lt.UUID=:UUID")
    public LoginToken findByUUID(@Param("UUID") String UUID);

    @Query("SELECT lt FROM LoginToken lt WHERE lt.user=:user")
    public List<LoginToken> findByUser(@Param("user") User user);
}
