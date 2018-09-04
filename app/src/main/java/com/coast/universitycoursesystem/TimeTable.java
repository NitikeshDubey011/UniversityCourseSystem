package com.coast.universitycoursesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coast.universitycoursesystem.Adapters.TimeTableAdapter;
import com.coast.universitycoursesystem.Models.TimeTableModel;
import com.coast.universitycoursesystem.Models.Username;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTable extends AppCompatActivity {
    ListView timeTable;
    Button submit;
    List<TimeTableModel> models;
    TimeTableModel timeTableModel;
    TimeTableAdapter timeTableAdapter;
    ArrayList<String> timeTableList = new ArrayList<>();
    String student_id = "", course_time_from = "", course_time_to = "";
    Date dateObj = null;
    Date dateSecond = null;
    String newDataArray;

    String URL_TIME_TABLE = "http://192.168.1.5/university/selectedCourses.php";
    String URL_TIME_TABLE_SUBMIT = "http://192.168.1.5/university/time.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        timeTable = (ListView) findViewById(R.id.table_data);
        submit = (Button) findViewById(R.id.submit_time_table);

        models = new ArrayList<>();
        timeTableAdapter = new TimeTableAdapter(TimeTable.this, models);
        for (Username username : AdminLogin.username) {
            student_id = username.getId();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TIME_TABLE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String formattedString = null;
                        String secondFormat = null;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String course_name = jsonObject.getString("course_id");
                                String course_day = jsonObject.getString("day");
                                course_time_from = jsonObject.getString("time_from");
                                course_time_to = jsonObject.getString("time_to");
                                String time_table_id = jsonObject.getString("id");
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                                    dateObj = sdf.parse(course_time_from);
                                    dateSecond = sdf.parse(course_time_to);
                                    formattedString = new SimpleDateFormat("hh:mm aa").format(dateObj);
                                    secondFormat = new SimpleDateFormat("hh:mm aa").format(dateSecond);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                models.add(new TimeTableModel(false, course_name, course_day, formattedString, secondFormat, time_table_id));
                                timeTableAdapter = new TimeTableAdapter(TimeTable.this, models);
                                timeTable.setAdapter(timeTableAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            e.getMessage();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TimeTable.this, "Network error!!", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("uid", student_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TimeTable.this);
        requestQueue.add(stringRequest);

        timeTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timeTableModel = models.get(position);
                if (timeTableModel.isSelected()) {
                    timeTableModel.setSelected(false);
                    String time_table_data = models.get(position).getTime_id();
                    timeTableList.remove(time_table_data);
                    Gson gson = new Gson();
                    newDataArray = gson.toJson(timeTableList);
                } else {
                    timeTableModel.setSelected(true);
                    String time_table_data = models.get(position).getTime_id();
                    timeTableList.add(time_table_data);
                    Gson gson = new Gson();
                    newDataArray = gson.toJson(timeTableList);
                }
                models.set(position, timeTableModel);
                timeTableAdapter.updateRecords(models);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataIntoTable();
            }
        });
    }

    public void addDataIntoTable() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TIME_TABLE_SUBMIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("status");
                            Log.i("response",success);
                            if (success.equals("1")) {
                                String name = jsonObject.getString("message");
                                Toast.makeText(TimeTable.this, name, Toast.LENGTH_SHORT).show();
                            }
                            if (success.equals("2")) {
                                String name = jsonObject.getString("message");
                                Toast.makeText(TimeTable.this, name, Toast.LENGTH_SHORT).show();
                            }

                            if (success.equals("3")) {
                                String name = jsonObject.getString("message");
                                Toast.makeText(TimeTable.this, name, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TimeTable.this,StudentView.class));
                                finish();
                            }

                            if (success.equals("4")) {
                                String name = jsonObject.getString("message");
                                Toast.makeText(TimeTable.this, name, Toast.LENGTH_SHORT).show();
                            }
                            Log.i("response",success);
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
                params.put("uid", student_id);
                params.put("check", newDataArray);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
