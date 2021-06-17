package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ProjectActivity extends AppCompatActivity {

    EditText editText1,editText2,editText3;
    Spinner spinner;
    Button addBtn;
    ImageButton dbBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);


        String email = getIntent().getExtras().getString("temp");

        editText1 = findViewById(R.id.P_name);
        editText2 = findViewById(R.id.P_Type);
        spinner = (Spinner) findViewById(R.id.P_spinner);
        editText3 = findViewById(R.id.P_Desc);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.P_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        addBtn = findViewById(R.id.P_addBtn);
        dbBtn = findViewById(R.id.PdbView);
        DBHelper dbHelper = new DBHelper(ProjectActivity.this);
        Cursor cursor = new DBHelper(this).readAllProjectData();

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText1.getText().toString();
                String type = editText2.getText().toString();
                String status = spinner.getSelectedItem().toString();
                String desc = editText3.getText().toString();

                String URL = Connection.API +"/create-project";

                if(name.isEmpty() || type.isEmpty() || status.isEmpty()|| desc.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Input All Fields",Toast.LENGTH_SHORT).show();

                }
                else {
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                        dbHelper.insertInProject(name, type, status, desc,"completed");
                        cursor.moveToFirst();
                        int id = cursor.getInt(0);
                        Log.d("ID IS :: ", String.valueOf(id));
                        Map<String, String> prams = new HashMap<String, String>();
                        prams.put("_id", String.valueOf(id));
                        prams.put("pName", name);
                        prams.put("pType", type);
                        prams.put("pStatus", status);
                        prams.put("pDescription", desc);
                        prams.put("status", "completed");

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(),response.getString("Message"), Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", response.getString("Message").toString());
                                    Intent intent = new Intent(ProjectActivity.this,AddActivity.class);
                                    {
                                        intent.putExtra("email",email);
                                        startActivity(intent);
                                        finish();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                String responseBody = null;
                                String Message = null;
                                try {
                                    responseBody = new String(error.networkResponse.data, "utf-8");
                                    JSONObject data = new JSONObject(responseBody);
                                    Message = data.getString("Message").toString();
                                } catch (UnsupportedEncodingException | JSONException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_SHORT).show();
                                Log.d("TAG", Message);
                            }
                        });

                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);
                    }
                    else {
                        dbHelper.insertInProject(name, type, status, desc,"pending");
                        Toast.makeText(getApplicationContext(), "ADDED", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProjectActivity.this, AddActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
                        finish();
                   }

                }

            }
        });

        dbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectActivity.this,ViewDBActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


}