package com.example.studentoneapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {

    @POST("register")
    Call<ResponseBody> createUser(
            @Body User user
    );

    @POST("authenticationservice/api/auth/signin")
    Call<ResponseLogin> checkUser(
            @Body LoginRequest user
    );

}
