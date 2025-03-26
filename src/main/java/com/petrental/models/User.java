package com.petrental.models;

public class User {
    private String email;
    private String password;

    public User() {}

    public User(String email, String password) {
        this.email = email;

        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{email='" + email + "', password='" + password + "'}";
    }

    public Object getPassword() {
        return password;
    }
}
