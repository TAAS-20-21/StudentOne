package com.example.studentoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private Button signOut, calendarButton;
    private TableLayout mTableLayout;
    private static List<Course> courses;
    private static List<Long> listTeacher;
    private static List<Long> listStudent;
    private static String[] buttonTexts = new String[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        mTableLayout = (TableLayout) findViewById(R.id.tableLayout);
        mTableLayout.setStretchAllColumns(true);

        UserTwo user = (UserTwo) getIntent().getSerializableExtra("user");
        String name = user.getName();
        String token = (String) getIntent().getSerializableExtra("access-token");

        signOut = findViewById(R.id.signOutButton);
        calendarButton = findViewById(R.id.calendarButton);


        System.out.println(user);
        //PROVA
        Call<List<Long>> callTeacher = com.example.studentoneapp.RetrofitClient
                .getInstance(RetrofitClient.COURSE_URL, token)
                .getAPI()
                .getCoursesByTeacher(user);

        callTeacher.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                try{
                    listTeacher = response.body();
                    System.out.println("Docente: " + listTeacher);
                } catch(Exception e) {
                    Toast.makeText(com.example.studentoneapp.HomeActivity.this, "Errore nella richiesta!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                Toast.makeText(com.example.studentoneapp.HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Call<List<Long>> callStudent = com.example.studentoneapp.RetrofitClient
                .getInstance(RetrofitClient.COURSE_URL, token)
                .getAPI()
                .getCoursesByStudent(user);

        callStudent.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                try{
                    listStudent = response.body();
                    System.out.println("Studenti: " + listStudent);
                } catch(Exception e) {
                    Toast.makeText(com.example.studentoneapp.HomeActivity.this, "Errore nella richiesta!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                Toast.makeText(com.example.studentoneapp.HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //PROVA

        Call<List<Course>> call = com.example.studentoneapp.RetrofitClient
                .getInstance(RetrofitClient.COURSE_URL, token)
                .getAPI()
                .getAllCourses();

        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                try{
                    courses = response.body();

                    TableRow firstRow = new TableRow(com.example.studentoneapp.HomeActivity.this);
                    final TextView nameFirstColumn = new TextView(HomeActivity.this);
                    final TextView nameSecondColumn = new TextView(HomeActivity.this);

                    nameFirstColumn.setText("Corsi insegnati");
                    nameFirstColumn.setTextSize(18);

                    nameSecondColumn.setText("Numero ore");
                    nameSecondColumn.setTextSize(18);

                    firstRow.addView(nameFirstColumn);
                    firstRow.addView(nameSecondColumn);


                    mTableLayout.addView(firstRow);

                    for(Course course : courses){
                        TableRow row = new TableRow(com.example.studentoneapp.HomeActivity.this);
                        System.out.println(course);

                        final TextView firstColumnValue = new TextView(HomeActivity.this);
                        final TextView secondColumnValue = new TextView(HomeActivity.this);
                        final Button thirdColumnValue = new Button(HomeActivity.this);

                        firstColumnValue.setText(course.getName());
                        firstColumnValue.setTextSize(16);
                        secondColumnValue.setText(course.getLesson_hours().toString());
                        secondColumnValue.setTextSize(16);
                        if(listStudent.isEmpty()) {
                            buttonTexts[0] = "insegna";
                            buttonTexts[1] = "non insegnare";
                            if(!listTeacher.contains(course.getId()))
                                thirdColumnValue.setText(buttonTexts[0]);
                            else
                                thirdColumnValue.setText(buttonTexts[1]);
                        }
                        else if(listTeacher.isEmpty()) {
                            buttonTexts[0] = "iscrivimi";
                            buttonTexts[1] = "disiscrivimi";
                            if(!listStudent.contains(course.getId()))
                                thirdColumnValue.setText(buttonTexts[0]);
                            else
                                thirdColumnValue.setText(buttonTexts[1]);
                        }
                        else
                            thirdColumnValue.setText("ERRORE");


                        row.addView(firstColumnValue);
                        row.addView(secondColumnValue);
                        row.addView(thirdColumnValue);

                        mTableLayout.addView(row);
                    }
                } catch(Exception e) {
                    Toast.makeText(com.example.studentoneapp.HomeActivity.this, "Errore nella richiesta, attenzione!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Toast.makeText(com.example.studentoneapp.HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // sign out
        signOut.setOnClickListener(new View.OnClickListener() {
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
        });
    }

    //da sistemare
    private void signOut() {
        Toast.makeText(HomeActivity.this, "logout completato", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
