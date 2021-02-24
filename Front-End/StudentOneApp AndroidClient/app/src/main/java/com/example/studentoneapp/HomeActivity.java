package com.example.studentoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeActivity extends AppCompatActivity {
    Button signOut, calendarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences preferences= com.example.studentoneapp.HomeActivity.this.getSharedPreferences("studentone", Context.MODE_PRIVATE);
        String accessToken  = preferences.getString("token",null);//second parameter default value.
        // sign out
        /*signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.signOutButton:
                        signOut();
                        break;
                }
            }
        });

        // calendar activity swap
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.calendarButton:
                        Intent intent = new Intent(HomeActivity.this, CalendarActivity.class);
                        startActivity(intent);
                }
            }
        });*/


    }

    //da sistemare
    private void signOut() {
        Toast.makeText(HomeActivity.this, "logout completato", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
