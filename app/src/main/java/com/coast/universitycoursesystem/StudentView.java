package com.coast.universitycoursesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coast.universitycoursesystem.Models.Username;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentView extends AppCompatActivity {
    Button logout_student_btn, enroll_Course_btn, select_time_table_btn;
    TextView name_title, student_name,name_of_course,name_title_student;
    ListView student_course;
    View views,views2;
    String URL_ENROLL = "http://192.168.1.5/university/home.php";
    private String string_username, string_id;
    String s1;
    ImageView imageView;
    ArrayList<String> datas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);
        logout_student_btn = (Button) findViewById(R.id.logout);
        enroll_Course_btn = (Button) findViewById(R.id.enroll);
        select_time_table_btn = (Button) findViewById(R.id.add_time_table);
        name_title = (TextView) findViewById(R.id.nameOfCurrentStudent);
        name_of_course=(TextView)findViewById(R.id.studentCourseTV);
        student_name = (TextView) findViewById(R.id.studentName2TV);
        student_course = (ListView) findViewById(R.id.studentCourse2TV);
        views=(View)findViewById(R.id.lowerBound);
        views2=(View)findViewById(R.id.upperBound);
        imageView=(ImageView)findViewById(R.id.imagesAt);
        name_title_student=(TextView)findViewById(R.id.studentNameTV);

        for (Username username : AdminLogin.username) {
            string_username = username.getName();
            string_id = username.getId();
        }
        student_name.setText(string_username.toUpperCase());
        name_title.setText("Welcome, " + string_username.toUpperCase());
        logout_student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentView.this, AdminLogin.class);
                startActivity(intent);
                finish();
            }
        });

        enroll_Course_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentView.this, EnrollCourse.class);
                startActivity(intent);

            }
        });


        select_time_table_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentView.this,TimeTable.class));
                finish();
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ENROLL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONArray array = new JSONArray(response);
                            Log.i("response",array.toString());
                            if(array.length()<=0){
                                Log.i("response","Empty");
                                name_of_course.setVisibility(View.GONE);
                                views.setVisibility(View.GONE);
                                student_course.setVisibility(View.GONE);
                                views2.setVisibility(View.GONE);
                                name_title_student.setVisibility(View.GONE);
                                student_name.setVisibility(View.GONE);
                                imageView.setBackgroundResource(R.drawable.nodata);

                            }
                            else{
                                name_of_course.setVisibility(View.VISIBLE);
                                views.setVisibility(View.VISIBLE);
                                student_course.setVisibility(View.VISIBLE);
                                views2.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                s1 = product.getString("course_name");
                                datas.add(s1);
                            }}
                            ArrayAdapter adapter = new ArrayAdapter<String>(StudentView.this, android.R.layout.simple_list_item_1, datas);
                            student_course.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StudentView.this, "Network connection error", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", string_id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
