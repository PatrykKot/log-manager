package com.kotlarz.configuration.security;

import com.kotlarz.configuration.Application;

public class AuthenticationConfiguration {
    public static final String LOGIN_URL = Application.APP_URL + "/login.html";
    public static final String LOGOUT_URL = Application.APP_URL + "/login.html?logout";
    public static final String LOGIN_FAILURE_URL = Application.APP_URL + "/login.html?error";
    public static final String LOGIN_PROCESSING_URL = Application.APP_URL + "/login";
}
