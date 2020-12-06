package com.project.repository;

import com.project.model.Link;
import com.project.model.LoginToken;
import com.project.model.User;
import com.project.repository.baserepo.MyEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends MyEntityRepository<Link> {

    @Query("SELECT l FROM Link l WHERE l.shortUrl=:shortUrl")
    public Link findByShortUrl(@Param("shortUrl")String shortUrl);

    @Query("SELECT l FROM Link l WHERE l.tokenId.user=:user")
    public List<Link> findByUser(@Param("user")User user);

    @Query("SELECT l from Link l WHERE l.tokenId=:tokenId")
    public List<Link> findByLoginToken(@Param("tokenId")LoginToken tokenId);
}
