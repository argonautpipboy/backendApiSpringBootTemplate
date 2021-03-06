package com.backend.springboot.template.security.jwt;

/**
 * Object to return as body in JWT Authentication
 */
public class JWTToken {
    private String idToken;

    public JWTToken(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
