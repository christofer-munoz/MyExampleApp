package com.example.myexampleapp;

import java.security.PublicKey;

public class User {

    int Id;
    String Name, Email, Password;

    public User() {

    }

    public User(String name, String email, String password) {
        Name = name;
        Email = email;
        Password = password;
    }

    public boolean isNull() {
        if(Name.equals("") || Email.equals("") || Password.equals("")) {
            return false;
        }
        return true;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}