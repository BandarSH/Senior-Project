package com.example.testingseniorproject1;

public class User {
    public String username, email, password;
    public double credit;

    public User(){}


    public User(String username, String email, String password, double credit){
        this.username = username;
        this.email = email;
        this.password = password;
        this.credit = credit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}
