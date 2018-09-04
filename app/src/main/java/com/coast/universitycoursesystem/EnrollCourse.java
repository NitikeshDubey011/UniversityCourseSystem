package com.coast.universitycoursesystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coast.universitycoursesystem.Adapters.CustomAdapter;
import com.coast.universitycoursesystem.Models.DataModel;
import com.coast.universitycoursesystem.Models.Username;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnrollCourse extends AppCompatActivity {
    Button enroll_Btn;
    String URL_REST = "http://192.168.1.5/university/restCourses.php";
    String URL_ENROLL = "http://192.168.1.5/university/enrolment.php";
    private String str_id;
    ListView listView;
    List<DataModel> models;
    CustomAdapter customAdapter;
    DataModel modelSet;
    RequestQueue queue;
    String newDataArray;
    int counter = 0;
    String name = "";
    ArrayList<String> arrList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_course);
        enroll_Btn = (Button) findViewById(R.id.crEnrol);
        listView = (ListView) findViewById(R.id.courseData);
        queue = Volley.newRequestQueue(this);

        models = new ArrayList<DataModel>();
        customAdapter = new CustomAdapter(EnrollCourse.this, models);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String dataInString = object.getString("course_name");
                                models.add(new DataModel(false, dataInString));
                                customAdapter = new CustomAdapter(EnrollCourse.this, models);
                                listView.setAdapter(customAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EnrollCourse.this, "Network error!!", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", str_id);
                return params;
            }
        };
        queue.add(stringRequest);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                modelSet = models.get(position);
                if (modelSet.isSelected()) {
                    modelSet.setSelected(false);
                    counter--;
                    String text = models.get(position).getUserName();
                    arrList.remove(text);
                    Gson gson = new Gson();
                    newDataArray = gson.toJson(arrList);

                } else {
                    modelSet.setSelected(true);
                    counter++;
                    String text = models.get(position).getUserName();
                    arrList.add(text);
                    Gson gson = new Gson();
                    newDataArray = gson.toJson(arrList);
                }
                models.set(position, modelSet);
                customAdapter.updateRecords(models);
            }
        });

        for (Username username : AdminLogin.username) {
            str_id = username.getId();
        }

        enroll_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }

        });

    }

    public void addData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ENROLL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");

                            if (success.equals("1")) {
                                String name = jsonObject.getString("message");
                                Toast.makeText(EnrollCourse.this, name, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EnrollCourse.this,TimeTable.class));
                                finish();

                            }

                            if (success.equals("2")) {
                                String name = jsonObject.getString("message");
                                Toast.makeText(EnrollCourse.this, name, Toast.LENGTH_SHORT).show();

                            }
                            if (success.equals("0")) {
                                String name = jsonObject.getString("message");
                                Toast.makeText(EnrollCourse.this, name, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            e.getMessage();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        error.getMessage();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", str_id);
                params.put("course", newDataArray);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}