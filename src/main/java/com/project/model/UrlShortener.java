package com.project.model;

import com.project.model.basemodel.MyEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "url_shortener")
public class UrlShortener extends MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    private int id;

    @Column(name = "short_url",nullable = false,unique = true)
    private String shortUrl;

    @Column(name = "long_url",nullable = false,length = 2083)
    private String longUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created",nullable = false)
    private Date createdDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "token_id")
    private LoginToken tokenId;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public LoginToken getTokenId() {
        return tokenId;
    }

    public void setTokenId(LoginToken tokenId) {
        this.tokenId = tokenId;
    }
}
