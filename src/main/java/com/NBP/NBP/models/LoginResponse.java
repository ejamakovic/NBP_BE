package com.NBP.NBP.models;

public class LoginResponse {
    private String token;
    private long expirationTimeMillis;
    private String username;

    public LoginResponse() {
    }

    public LoginResponse(String token, long expirationTimeMillis, String username) {
        this.token = token;
        this.expirationTimeMillis = expirationTimeMillis;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpirationTimeMillis() {
        return expirationTimeMillis;
    }

    public void setExpirationTimeMillis(long expirationTimeMillis) {
        this.expirationTimeMillis = expirationTimeMillis;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", expirationTime=" + expirationTimeMillis +
                ", username='" + username + '\'' +
                '}';
    }

}
