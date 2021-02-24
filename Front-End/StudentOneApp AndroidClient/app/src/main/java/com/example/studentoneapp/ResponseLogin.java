package com.example.studentoneapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("user")
    @Expose
    private com.example.studentoneapp.UserTwo user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public com.example.studentoneapp.UserTwo getUser() {
        return user;
    }

    public void setUser(com.example.studentoneapp.UserTwo user) {
        this.user = user;
    }

}