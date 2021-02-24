package com.example.studentoneapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {

    @POST("authenticationservice/api/auth/signup")
    Call<ResponseBody> createUser(
            @Body RegisterRequest user
    );

    @POST("authenticationservice/api/auth/signin")
    Call<ResponseLogin> checkUser(
            @Body LoginRequest user
    );

}
