package com.coast.universitycoursesystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.coast.universitycoursesystem.Models.Username;


public class AdminControl extends AppCompatActivity {
    Button Add_btn, Show_student_list, logout_btn;
    TextView Show_students, title_name;
    RequestQueue requestQueue;
    private String string_username;
//    String showStudent = "http://127.0.0.1/coast_university_data/showStudent.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);
        Add_btn = (Button) findViewById(R.id.add_student_admin);
        logout_btn = (Button) findViewById(R.id.logout_admin);
        title_name = (TextView) findViewById(R.id.nameOfStudent);
//        Show_students = (TextView) findViewById(R.id.show_students_s);
//        Show_student_list = (Button) findViewById(R.id.show_student_admin);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdminControl.this, AddStudent.class);
                startActivity(intent2);
            }
        });
        for (Username username : AdminLogin.username) {
            string_username = username.getName();
        }
        title_name.setText("Welcome, " + string_username.toUpperCase());
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutINtent = new Intent(AdminControl.this, AdminLogin.class);
                startActivity(logoutINtent);
                finish();
            }
        });
    }

}
