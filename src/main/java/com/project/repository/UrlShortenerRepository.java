package com.project.repository;

import com.project.model.UrlShortener;
import com.project.model.LoginToken;
import com.project.model.User;
import com.project.repository.baserepo.MyEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlShortenerRepository extends MyEntityRepository<UrlShortener> {

    public Optional<UrlShortener> findByShortUrl(String shortUrl);
    public Optional<UrlShortener> findByLongUrl(String longUrl);
    public List<UrlShortener> findByTokenId(LoginToken tokenId);
}
