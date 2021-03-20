package com.example.studentoneapp;


import android.util.Base64;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private String AUTH ="";
    public static  final String BASE_URL = "http://10.0.2.2:8080/StudentOne/";
    public static final String AUTH_URL = BASE_URL + "authenticateservice/api/";
    public static  final String COURSE_URL = "http://10.0.2.2:8080/StudentOne/courseservice/api/";
    private static com.example.studentoneapp.RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient (String url, final String token) {

        if(token!=null){
            AUTH = "Basic " + Base64.encodeToString((token).getBytes(), Base64.NO_WRAP);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request original = chain.request();

                                    Request.Builder requestBuilder = original.newBuilder()
                                            .addHeader("Authorization", "Bearer " + token)
                                            .method(original.method(), original.body());

                                    Request request = requestBuilder.build();
                                    return chain.proceed(request);
                                }
                            }
                    ).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

    }

    public static synchronized com.example.studentoneapp.RetrofitClient getInstance(String url, String token) {

        mInstance = new com.example.studentoneapp.RetrofitClient(url, token);

        return mInstance;
    }

    public com.example.studentoneapp.API getAPI () {
        return retrofit.create(com.example.studentoneapp.API.class);
    }

}