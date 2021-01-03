package com.project.services;

import com.project.dto.UrlShortenerResponse;
import com.project.model.LoginToken;
import com.project.model.UrlShortener;
import com.project.repository.UrlShortenerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.DateUtils;

import java.util.*;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlShortenerRepository repository;

    @Transactional
    public void saveUrl(UrlShortener entity) {
        repository.save(entity);
    }

    public String shorten(String longUrl, LoginToken loginToken) {

        final String encryptUrl = getEncryptUrl();

        saveUrl(UrlShortener.UrlShortenerBuilder.anUrlShortener()
                .longUrl(longUrl)
                .shortUrl(encryptUrl)
                .createdDate(DateUtils.createNow().getTime())
                .tokenId(loginToken)
                .build());

        UrlShortenerResponse response = new UrlShortenerResponse();
        response.setLongUrl(longUrl);
        response.setShortUrl(encryptUrl);
        return response.getShortUrl();
    }

    public Optional<UrlShortener> findByShortUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl);
    }

    public List<UrlShortener> findByTokenId(LoginToken tokenId) {
        return repository.findByTokenId(tokenId);
    }

    private String getEncryptUrl(){
        List<Character> list = new ArrayList<>();
        for (char i='a'; i<='z'; i++) list.add(i);
        for (char i='A'; i<='Z'; i++) list.add(i);
        for (char i='0'; i<='9'; i++) list.add(i);

        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<4; i++ ){
            builder.append(list.get(random.nextInt(list.size())));
        }
        return repository.findByShortUrl(builder.toString())
                .map(UrlShortener::getShortUrl)
                .orElse(builder.toString());
    }
}
