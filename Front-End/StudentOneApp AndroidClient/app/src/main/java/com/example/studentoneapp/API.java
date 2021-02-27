package com.example.studentoneapp;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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

    @POST("teacher/courses")
    Call<List<Long>> getCoursesByTeacher(
            @Body UserTwo user
    );

    @POST("student/courses")
    Call<List<Long>> getCoursesByStudent(
            @Body UserTwo user
    );

    @GET("course")
    Call<List<Course>> getAllCourses();

    @POST("user/isProfessor")
    Call<Boolean> getIsProfessor(
            @Body UserTwo user
    );

}
