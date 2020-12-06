package com.project.model;

import com.project.model.basemodel.MyEntity;
import com.project.util.Constants;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="t_users")
public class User extends MyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false,unique = true)
    private int id;

    @Column(name = "user_email",nullable = false,unique = true,length = 320)
    private String email;

    @Column(name = "user_password",nullable = false,unique = true,length = 60)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_date_created",nullable = false)
    private Date dateCreated = new Date();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<LoginToken> loginTokenList = new LinkedList<>();

    public void bcryptAndSetPassword(String password){
        this.password = Constants.getPasswordEncoder().encode(password);
    }

    public boolean checkPassword(String password){
        return Constants.getPasswordEncoder().matches(password,getPassword());
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<LoginToken> getLoginTokenList() {
        return loginTokenList;
    }

    public void setLoginTokenList(List<LoginToken> loginTokenList) {
        this.loginTokenList = loginTokenList;
    }
}
