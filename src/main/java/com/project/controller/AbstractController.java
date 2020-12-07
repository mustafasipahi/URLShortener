package com.project.controller;

import com.project.model.LoginToken;
import com.project.model.User;
import com.project.services.LoginTokenService;
import com.project.util.Constants;
import com.project.util.LoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractController {

    @Autowired
    private LoginTokenService loginTokenService;

    public HttpServletRequest getHttpRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest();
    }

    public HttpServletResponse getHttpResponse(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getResponse();
    }

    private Cookie getCookie(Cookie[] cookies, String name){
        if (cookies == null){
            return null;
        }
        for(Cookie cookie : cookies){
            if (cookie.getName().equals(name)) return cookie;
        }
        return null;
    }

    public LoginToken getLoginToken(){
        HttpServletRequest request = getHttpRequest();
        Cookie[] cookies = request.getCookies();
        Cookie cookie = getCookie(cookies, Constants.LOGGIN_TOKEN_COOKIE_NAME);
        LoginToken loginToken = null;
        if (cookie == null){
            loginToken = createAnonymousLoginToken();
        }else {
            loginToken = loginTokenService.findByUUID(cookie.getValue());
            if (loginToken == null || !loginToken.isValid()){
                loginToken = createAnonymousLoginToken();
            }
        }
        return loginToken;
    }

    private LoginToken createAnonymousLoginToken(){
        HttpServletResponse response = getHttpResponse();
        LoginToken loginToken = loginTokenService.saveAnonymousLoginToken();
        Cookie cookie = new Cookie(Constants.LOGGIN_TOKEN_COOKIE_NAME,loginToken.getUUID());
        cookie.setMaxAge((int) (loginToken.getExpiration_duration()/1000));
        response.addCookie(cookie);
        return loginToken;
    }

    public boolean isLoggedIn(){
        LoginToken loginToken = getLoginToken();
        return (loginToken.getType() == LoginType.LOGGED_IN);
    }

    public boolean isAnonymous(){
        LoginToken loginToken = getLoginToken();
        return (loginToken.getType() == LoginType.ANONYMOUS);
    }
    public User getLoggedInUser(){
        if (isLoggedIn()){
            return getLoginToken().getUser();
        }
        return null;
    }
}
