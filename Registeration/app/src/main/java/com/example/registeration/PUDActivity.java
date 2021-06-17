package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class PUDActivity extends AppCompatActivity {

    TextView tvHeader;
    EditText etName,etType,etDesc;
    Spinner etStatus;
    Button btnUpdate , btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pudactivity);

        etName = findViewById(R.id.ETU_Name);
        etType = findViewById(R.id.ETU_Type);
        etStatus = findViewById(R.id.Spn_Status);
        etDesc = findViewById(R.id.ETU_Desc);
        tvHeader = findViewById(R.id.TV_Header);
        btnUpdate = findViewById(R.id.btnPRUpdate);
        btnDelete = findViewById(R.id.btnPRDelete);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        String header = getIntent().getExtras().getString("pHeader");
        int id = getIntent().getExtras().getInt("pID");
        String pData = getIntent().getExtras().getString("pData");
        tvHeader.setText(header);
        Toast.makeText(getApplicationContext(),header, Toast.LENGTH_SHORT).show();
        if(header.equals("PROJECT DETAIL")) {

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.P_status_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            etStatus.setAdapter(adapter);
        }
        else{
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.R_status_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            etStatus.setAdapter(adapter);
        }

            String[] infos = pData.split("/");
            etName.setText(infos[0]);
            etType.setText(infos[1]);
            etDesc.setText(infos[3]);




        DBHelper dbHelper = new DBHelper(PUDActivity.this);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(header.equals("PROJECT DETAIL")){
                    String URL = Connection.API +"/update-project/"+id;
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                        Map<String, String> prams = new HashMap<String, String>();
                        prams.put("pName", etName.getText().toString());
                        prams.put("pType", etType.getText().toString());
                        prams.put("pStatus", etStatus.getSelectedItem().toString());
                        prams.put("pDescription", etDesc.getText().toString());
                        prams.put("status","completed");
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_SHORT).show();
                                    dbHelper.updateProjectData(id,etName.getText().toString(),etType.getText().toString(),etStatus.getSelectedItem().toString(),etDesc.getText().toString(),"completed");
                                    Intent intent = new Intent(PUDActivity.this,ViewDBActivity.class);
                                    startActivity(intent);
                                    finish();
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
                                    Message = data.getString("error").toString();
                                    Toast.makeText(getApplicationContext(),Message, Toast.LENGTH_SHORT).show();
                                } catch (UnsupportedEncodingException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please Connect Internet", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    String URL = Connection.API +"/update-resource/"+id;
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                        Map<String, String> prams = new HashMap<String, String>();
                        prams.put("rName", etName.getText().toString());
                        prams.put("rType", etType.getText().toString());
                        prams.put("rStatus", etStatus.getSelectedItem().toString());
                        prams.put("rDescription", etDesc.getText().toString());
                        prams.put("status","completed");
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_SHORT).show();
                                    dbHelper.updateResourceData(id,etName.getText().toString(),etType.getText().toString(),etStatus.getSelectedItem().toString(),etDesc.getText().toString(),"completed");
                                    Intent intent = new Intent(PUDActivity.this,ViewDBActivity.class);
                                    startActivity(intent);
                                    finish();
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
                                    Message = data.getString("error").toString();
                                    Toast.makeText(getApplicationContext(),Message, Toast.LENGTH_SHORT).show();
                                } catch (UnsupportedEncodingException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please Connect Internet", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(header.equals("PROJECT DETAIL")){
                    String URL = Connection.API +"/delete-project/"+id;
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                        dbHelper.deleteSpecificProject(id);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, URL, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PUDActivity.this,ViewDBActivity.class);
                                    startActivity(intent);
                                    finish();
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
                                    Message = data.getString("error").toString();
                                    Toast.makeText(getApplicationContext(),Message, Toast.LENGTH_SHORT).show();
                                } catch (UnsupportedEncodingException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please Connect Internet", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    String URL = Connection.API +"/delete-resource/"+id;
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                        dbHelper.deleteSpecificResource(id);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, URL, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PUDActivity.this,ViewDBActivity.class);
                                    startActivity(intent);
                                    finish();
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
                                    Message = data.getString("error").toString();
                                    Toast.makeText(getApplicationContext(),Message, Toast.LENGTH_SHORT).show();
                                } catch (UnsupportedEncodingException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Please Connect Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}