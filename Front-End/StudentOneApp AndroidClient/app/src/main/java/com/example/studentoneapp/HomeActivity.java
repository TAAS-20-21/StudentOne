package com.example.studentoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
    private static List<Long> listCoursesAttended;
    private static String[] buttonTexts = new String[2];
    private static Boolean isProfessor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mTableLayout = (TableLayout) findViewById(R.id.tableLayout);
        mTableLayout.setStretchAllColumns(true);
        UserTwo user = (UserTwo) getIntent().getSerializableExtra("user");
        String token = (String) getIntent().getSerializableExtra("access-token");
        String name = user.getName();


        signOut = findViewById(R.id.signOutButton);
        calendarButton = findViewById(R.id.calendarButton);


        System.out.println(user);

        Call<Boolean> callIsProfessor = com.example.studentoneapp.RetrofitClient
                .getInstance(RetrofitClient.COURSE_URL, token)
                .getAPI()
                .getIsProfessor(user);

        callIsProfessor.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                try {
                    isProfessor = response.body();
                    System.out.println("isProfessor" + isProfessor);
                    initializeCourses(user, token);
                    initializeButtons(user, token);
                } catch (Exception e) {
                    Toast.makeText(com.example.studentoneapp.HomeActivity.this, "Errore nella richiesta!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
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
                        Intent intent = new Intent(HomeActivity.this, CalendarActivity.class).putExtra("user", user).putExtra("access-token", token);
                        startActivity(intent);
                        finish();
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

    private void initializeCourses(UserTwo user, String token) {
        if (isProfessor) {
            Call<List<Long>> callTeacher = com.example.studentoneapp.RetrofitClient
                    .getInstance(RetrofitClient.COURSE_URL, token)
                    .getAPI()
                    .getCoursesByTeacher(user);

            callTeacher.enqueue(new Callback<List<Long>>() {
                @Override
                public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                    try {
                        listCoursesAttended = response.body();
                        System.out.println("Docente: " + listCoursesAttended);
                    } catch (Exception e) {
                        Toast.makeText(com.example.studentoneapp.HomeActivity.this, "Errore nella richiesta!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Long>> call, Throwable t) {
                    Toast.makeText(com.example.studentoneapp.HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Call<List<Long>> callStudent = com.example.studentoneapp.RetrofitClient
                    .getInstance(RetrofitClient.COURSE_URL, token)
                    .getAPI()
                    .getCoursesByStudent(user);

            callStudent.enqueue(new Callback<List<Long>>() {
                @Override
                public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                    try {
                        listCoursesAttended = response.body();
                        System.out.println("Studenti: " + listCoursesAttended);
                    } catch (Exception e) {
                        Toast.makeText(com.example.studentoneapp.HomeActivity.this, "Errore nella richiesta!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Long>> call, Throwable t) {
                    Toast.makeText(com.example.studentoneapp.HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void initializeButtons(UserTwo user,String token) {
        Call<List<Course>> callCourses = com.example.studentoneapp.RetrofitClient
                .getInstance(RetrofitClient.COURSE_URL, token)
                .getAPI()
                .getAllCourses();

        callCourses.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                try {
                    courses = response.body();

                    TableRow firstRow = new TableRow(com.example.studentoneapp.HomeActivity.this);
                    final TextView nameFirstColumn = new TextView(HomeActivity.this);
                    final TextView nameSecondColumn = new TextView(HomeActivity.this);

                    nameFirstColumn.setText("Corsi insegnati");
                    nameFirstColumn.setTextSize(18);
                    nameFirstColumn.setTextColor(Color.BLACK);

                    nameSecondColumn.setText("Numero ore");
                    nameSecondColumn.setTextSize(18);
                    nameSecondColumn.setTextColor(Color.BLACK);

                    firstRow.addView(nameFirstColumn);
                    firstRow.addView(nameSecondColumn);


                    mTableLayout.addView(firstRow);

                    for (Course course : courses) {
                        TableRow row = new TableRow(com.example.studentoneapp.HomeActivity.this);
                        System.out.println(course);

                        final TextView firstColumnValue = new TextView(HomeActivity.this);
                        final TextView secondColumnValue = new TextView(HomeActivity.this);
                        final Button thirdColumnValue = new Button(HomeActivity.this);

                        firstColumnValue.setText(course.getName());
                        firstColumnValue.setTextSize(16);
                        secondColumnValue.setText(course.getLesson_hours().toString());
                        secondColumnValue.setTextSize(16);
                        if (isProfessor) {
                            buttonTexts[0] = "insegna";
                            buttonTexts[1] = "non insegnare";
                        } else {
                            buttonTexts[0] = "iscrivimi";
                            buttonTexts[1] = "disiscrivimi";
                        }

                        thirdColumnValue.setId(course.getId().intValue());

                        if (!listCoursesAttended.contains(course.getId())) {
                            thirdColumnValue.setText(buttonTexts[0]);
                            thirdColumnValue.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Long courseId = new Long(v.getId());
                                    Long personId = Long.parseLong(user.getId());

                                    CourseUserObject courseUser = new CourseUserObject(courseId, personId);
                                    listCoursesAttended.add(courseId);
                                    addCourseUser(courseUser, token);
                                    thirdColumnValue.setText(buttonTexts[1]);
                                    finish();
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                }
                            });
                        }
                        else {
                            thirdColumnValue.setText(buttonTexts[1]);
                            thirdColumnValue.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Long courseId = new Long(v.getId());
                                    Long personId = Long.parseLong(user.getId());

                                    CourseUserObject courseUser = new CourseUserObject(courseId, personId);
                                    listCoursesAttended.remove(courseId);
                                    deleteCourseUser(courseUser,token);
                                    thirdColumnValue.setText(buttonTexts[0]);
                                    finish();
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                }
                            });
                        }



                        row.addView(firstColumnValue);
                        row.addView(secondColumnValue);
                        row.addView(thirdColumnValue);

                        mTableLayout.addView(row);
                    }
                } catch (Exception e) {
                    Toast.makeText(com.example.studentoneapp.HomeActivity.this, "Errore nella richiesta, attenzione!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Toast.makeText(com.example.studentoneapp.HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addCourseUser(CourseUserObject courseUser, String token) {
        if (!isProfessor) {
            Call<Object> callAddCourseStudent = com.example.studentoneapp.RetrofitClient
                    .getInstance(RetrofitClient.COURSE_URL, token)
                    .getAPI()
                    .addLikedCourse(courseUser);

            callAddCourseStudent.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    try {
                        System.out.println(response.body());
                    } catch (Exception e) {
                        Toast.makeText(com.example.studentoneapp.HomeActivity.this, "Errore nella richiesta!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Toast.makeText(com.example.studentoneapp.HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Call<Object> callAddCourseTeacher = com.example.studentoneapp.RetrofitClient
                    .getInstance(RetrofitClient.COURSE_URL, token)
                    .getAPI()
                    .addAssignedCourse(courseUser);

            callAddCourseTeacher.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    try {
                        System.out.println(response.body());
                    } catch (Exception e) {
                        Toast.makeText(com.example.studentoneapp.HomeActivity.this, "Errore nella richiesta!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Toast.makeText(com.example.studentoneapp.HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    private void deleteCourseUser(CourseUserObject courseUser, String token) {
        if (!isProfessor) {
            Call<Object> callDeleteCourseStudent = com.example.studentoneapp.RetrofitClient
                    .getInstance(RetrofitClient.COURSE_URL, token)
                    .getAPI()
                    .deleteLikedCourse(courseUser);

            callDeleteCourseStudent.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    try {
                        System.out.println(response.body());
                    } catch (Exception e) {
                        Toast.makeText(com.example.studentoneapp.HomeActivity.this, "Errore nella richiesta!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Toast.makeText(com.example.studentoneapp.HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Call<Object> calldeleteCourseTeacher = com.example.studentoneapp.RetrofitClient
                    .getInstance(RetrofitClient.COURSE_URL, token)
                    .getAPI()
                    .deleteAssignedCourse(courseUser);

            calldeleteCourseTeacher.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    try {
                        System.out.println(response.body());
                    } catch (Exception e) {
                        Toast.makeText(com.example.studentoneapp.HomeActivity.this, "Errore nella richiesta!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Toast.makeText(com.example.studentoneapp.HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
