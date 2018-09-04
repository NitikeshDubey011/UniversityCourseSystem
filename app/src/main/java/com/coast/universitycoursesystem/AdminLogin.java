package com.coast.universitycoursesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class AdminLogin extends AppCompatActivity {
    EditText email_admin, password_admin;
    Button loginBtn;
    private ProgressBar loading;
    String success;
    private static String URL_LOGIN = "http://192.168.1.5/university/login.php";
    public static ArrayList<Username> username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        email_admin = (EditText) findViewById(R.id.email);
        password_admin = (EditText) findViewById(R.id.pass);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loading = findViewById(R.id.loading);
        username = new ArrayList<>();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email_admin.getText().toString().trim();
                String mPass = password_admin.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPass.isEmpty()) {
                    Login(mEmail, mPass);
                } else {
                    email_admin.setError("Please insert email");
                    password_admin.setError("Please insert password");
                }
            }
        });


    }

    private void Login(final String email, final String password) {

        loading.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("login");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    String type = object.getString("type").trim();
                                    String id = object.getString("uid").trim();


                                    if (type.equals("A")) {
                                        username.add(new Username(name,id));
                                        Intent intent = new Intent(AdminLogin.this, AdminControl.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        username.add(new Username(name,id));

//                                        Toast.makeText(AdminLogin.this, name+" \n"+email+" \n"+id+" \n"+type, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AdminLogin.this, StudentView.class);
                                        startActivity(intent);
                                        finish();
                                        loading.setVisibility(View.GONE);
                                    }
                                }

                            }

                        } catch (JSONException e) {
                            Log.e("my app", "i got error", e);
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            loginBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(AdminLogin.this, "Error123 " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        loginBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(AdminLogin.this, "Error11 " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("pass", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}