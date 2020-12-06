package com.project.model;

import com.project.model.basemodel.MyEntity;
import com.project.util.LoginType;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "t_login_tokens")
public class LoginToken extends MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id",nullable = false,unique = true)
    private int id;

    @Column(name = "token_UUID",nullable = false,unique = true,length = 36)
    private String UUID;
                                    // (EnumType.ORDINAL) =   Enum değeri yazar 0/1 gibi
    @Enumerated(EnumType.ORDINAL)    // (EnumType.STRING)  =   Enum ismini yazar ne yazıldıysa
    @Column(name = "token_type",nullable = false)
    private LoginType type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "token_date_created")
    private Date dateCreated = new Date();

    @Column(name = "token_expiration_duration",nullable = false)
    private Long expiration_duration = 0L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "token_date_logged_out")
    private Date dateLoggedOut = null;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")     // Veri Tabanında Join işlemi Yapıyoruz Demek
    private User user = null;

    @OneToMany(mappedBy = "tokenId",fetch = FetchType.LAZY)
    private List<Link> links = new LinkedList<>();

    public boolean isExpired(){
        Date expirationDate = new Date(dateCreated.getTime() + expiration_duration);
        return expirationDate.compareTo(new Date()) < 0;
    }

    public boolean isLoggedOut(){
        return dateLoggedOut != null;
    }

    public boolean isValid(){
        return !isLoggedOut() && !isExpired();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public LoginType getType() {
        return type;
    }

    public void setType(LoginType type) {
        this.type = type;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getExpiration_duration() {
        return expiration_duration;
    }

    public void setExpiration_duration(Long expiration_duration) {
        this.expiration_duration = expiration_duration;
    }

    public Date getDateLoggedOut() {
        return dateLoggedOut;
    }

    public void setDateLoggedOut(Date dateLoggedOut) {
        this.dateLoggedOut = dateLoggedOut;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
