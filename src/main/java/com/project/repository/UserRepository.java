package com.project.repository;

import com.project.model.User;
import com.project.repository.baserepo.MyEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MyEntityRepository<User> {

    //@Query("SELECT u FROM User u WHERE u.email=:email")
    public Optional<User> findByEmail(@Param("email") String email);
}
