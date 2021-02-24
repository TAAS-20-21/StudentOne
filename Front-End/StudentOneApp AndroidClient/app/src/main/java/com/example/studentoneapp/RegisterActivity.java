package com.example.studentoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.studentoneapp.R.id.etRPassword;


public class RegisterActivity extends AppCompatActivity {

    private EditText etName,etSurname, etEmail, etPassword, etRepeatPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etRname);
        etSurname = findViewById(R.id.etRsurname);
        etEmail =  findViewById(R.id.etEmail);
        etPassword = findViewById(etRPassword);

        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        findViewById(R.id.tvLoginLink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(com.example.studentoneapp.RegisterActivity.this, com.example.studentoneapp.LoginActivity.class));
            }
        });
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Inserisci il nome");
            etName.requestFocus();
            return;
        } else if (password.isEmpty()) {
            etPassword.setError("Inserisci la password");
            etPassword.requestFocus();
            return;
        } else if (surname.isEmpty()) {
            etSurname.setError("Inserisci il cognome");
            etSurname.requestFocus();
            return;
        } else if (email.isEmpty()){
            etEmail.setError("Inserisci la mail");
            etSurname.requestFocus();
        }

        Call<ResponseBody> call = com.example.studentoneapp.RetrofitClient
                .getInstance(RetrofitClient.BASE_URL, null)
                .getAPI()
                .createUser(new RegisterRequest(email, password, name, surname));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(com.example.studentoneapp.RegisterActivity.this, "aa", Toast.LENGTH_LONG).show();
                /*
                String s = "";
                try {
                    s = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (s.equals("SUCCESS")) {
                    Toast.makeText(com.example.studentoneapp.RegisterActivity.this, "Successfully registered. Please login", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(com.example.studentoneapp.RegisterActivity.this, com.example.studentoneapp.LoginActivity.class));
                } else {
                    Toast.makeText(com.example.studentoneapp.RegisterActivity.this, "User already exists!", Toast.LENGTH_LONG).show();
                }*/
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(com.example.studentoneapp.RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}