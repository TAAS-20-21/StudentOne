package com.example.studentoneapp;

public class RegisterRequest {
        private String email;
        private String password;
        private String name;
        private String surname;
        private String socialProvider;

        public RegisterRequest(String email,String password, String name, String surname){
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.password = password;
            this.socialProvider = "LOCAL";
        }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}