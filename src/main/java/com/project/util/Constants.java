package com.project.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class Constants {

    private static final long DAY_MILLES = 24L*60*60*1000;
    public static final Long ANONYMOUS_LOGIN_TOKEN_EXPIRATION_DURATION = 7L*DAY_MILLES;
    public static final Long LOGGED_IN_LOGIN_TOKEN_EXPIRATION_DURATION = 365L*DAY_MILLES;
    public static final String LOGGIN_TOKEN_COOKIE_NAME = "login-token";
    public static final String LOCAL_HOST = "http://localhost:8080/";

    private static BCryptPasswordEncoder passwordEncoder = null;

    public static BCryptPasswordEncoder getPasswordEncoder() {
        if (passwordEncoder == null){
            passwordEncoder = new BCryptPasswordEncoder();
        }
        return passwordEncoder;
    }
}
