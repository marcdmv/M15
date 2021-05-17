package com.example.demo.models;

public class User {

    private String user;
    private String pwd;
    private String token;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPwd() {
        return pwd;
    }

    public String getToken() {
        return token;
    }
}
