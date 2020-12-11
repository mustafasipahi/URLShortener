package com.project.services;

import com.project.dto.UrlShortenerResponse;
import com.project.model.LoginToken;
import com.project.model.UrlShortener;
import com.project.repository.UrlShortenerRepository;
import com.project.services.baseservice.MyEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UrlShortenerService implements MyEntityService<UrlShortener> {

    private static final String LOCAL_HOST = "http://localhost:8080/";

    @Autowired
    private UrlShortenerRepository repository;

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public UrlShortener save(UrlShortener entity) {
        return repository.save(entity);
    }

    public String  shorten(String request, LoginToken loginToken) {
        String encryptUrl = getEncryptUrl(request, loginToken);
        UrlShortenerResponse response = new UrlShortenerResponse();
        response.setLongUrl(request);
        response.setShortUrl(encryptUrl);
        return response.getShortUrl();
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<UrlShortener> findById(Integer id) {
        return repository.findById(id);
    }

    public Optional<UrlShortener> findByShortUrl(String shortUrl) {
        return repository.findByShortUrl(shortUrl);
    }

    public Optional<UrlShortener> findByLongUrl(String longUrl) {
        return repository.findByLongUrl(longUrl);
    }

    @Override
    public List<UrlShortener> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existById(Integer id) {
        return findById(id).isPresent();
    }

    public String getEncryptUrl(String longUrl, LoginToken loginToken){

        Optional<UrlShortener> byLongUrl = repository.findByLongUrl(longUrl);
        if (byLongUrl.isPresent()){
            return byLongUrl.get().getShortUrl();
        }
        String key = "";

        do {
            StringBuilder builder = new StringBuilder("MSS-");
            for (int i=0; i<4; i++) builder.append(getRandomCharacter());
            key = builder.toString();
        }while (existUrl(key));

        UrlShortener urlShortener = new UrlShortener();
        urlShortener.setLongUrl(longUrl);
        urlShortener.setShortUrl(LOCAL_HOST + key);
        urlShortener.setCreatedDate(new Date());
        urlShortener.setTokenId(loginToken);
        repository.save(urlShortener);
        return LOCAL_HOST + key;
    }

    private boolean existUrl(String key){
        return findByShortUrl(LOCAL_HOST + key).isPresent();
    }

    private char getRandomCharacter(){
        List<Character> list = new ArrayList<>();
        for (char i='a'; i<='z'; i++) list.add(i);
        for (char i='A'; i<='Z'; i++) list.add(i);
        for (char i='0'; i<='9'; i++) list.add(i);
        return list.get(new Random().nextInt(list.size()));
    }
}
