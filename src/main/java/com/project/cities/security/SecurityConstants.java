package com.project.cities.security;

import com.project.cities.SpringApplicationContext;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ALL_CITIES = "/get-cities";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/cities";
    public static final String LOGIN_URL = "/login";
    public static final String TOKEN_SECRET = "jf9i4jgu83nfl024242";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
        return appProperties.getTokenSecret();
    }
}