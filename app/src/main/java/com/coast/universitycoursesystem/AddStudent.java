package com.coast.universitycoursesystem;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddStudent extends AppCompatActivity {

    EditText name_student, email_student, password_student, phone_student;
    Button AddStudent;
    RadioGroup radioGroupGender;
    ProgressBar loading;
    private static String URL_REGIST = "http://192.168.1.5/university/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        name_student = (EditText) findViewById(R.id.name);
        email_student = (EditText) findViewById(R.id.email);
        password_student = (EditText) findViewById(R.id.password_student);
        phone_student = (EditText) findViewById(R.id.phone);
        AddStudent = (Button) findViewById(R.id.add_student);
        radioGroupGender = (RadioGroup) findViewById(R.id.gender);
        loading=(ProgressBar)findViewById(R.id.loadingsecond);


        AddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regist();
            }
        });


    }

    private void Regist() {
        loading.setVisibility(View.VISIBLE);
        AddStudent.setVisibility(View.GONE);

        final String username = name_student.getText().toString().trim();
        final String email = email_student.getText().toString().trim();
        final String password = password_student.getText().toString().trim();
        final String phone = phone_student.getText().toString().trim();
        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();
//        Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();
        //first we will do the validations

        if (TextUtils.isEmpty(username)) {
            name_student.setError("Please enter username");
            name_student.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            email_student.setError("Please enter your email");
            email_student.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_student.setError("Enter a valid email");
            email_student.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            password_student.setError("Enter a password");
            password_student.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            phone_student.setError("Enter a phone number");
            phone_student.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(AddStudent.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddStudent.this, AdminControl.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddStudent.this, "Register Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            AddStudent.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddStudent.this, "Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        AddStudent.setVisibility(View.VISIBLE);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                params.put("name", username);
                params.put("email", email);
                params.put("password_student", password);
                params.put("phone", phone);
                params.put("gender", gender);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


}







