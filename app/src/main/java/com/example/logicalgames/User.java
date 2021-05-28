package com.example.logicalgames;

public class User {
    public String id, login;
    long rating;

    public User() {
    }

    public User(String id, String login, long rating) {
        this.id = id;
        this.login = login;
        this.rating = rating;
    }
}
