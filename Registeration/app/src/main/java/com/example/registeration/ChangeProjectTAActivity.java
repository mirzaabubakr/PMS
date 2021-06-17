package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

public class ChangeProjectTAActivity extends AppCompatActivity {

    EditText cptName,cptReq,cptDate,cptReqName,cptDesc,cptRes;
    Spinner cptImpact;
    Button addBtn;
    ImageButton dbBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_project_taactivity);

        String email = getIntent().getExtras().getString("temp");

        cptName = findViewById(R.id.cpt_name);
        cptReq = findViewById(R.id.cpt_requestby);
        cptDate = findViewById(R.id.cpt_date);
        cptReqName = findViewById(R.id.cpt_reqName);
        cptDesc = findViewById(R.id.cpt_cdesc);
        cptRes = findViewById(R.id.cpt_reason);
        cptImpact = (Spinner) findViewById(R.id.cpt_impact);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cpta_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cptImpact.setAdapter(adapter);
        addBtn = findViewById(R.id.cpt_submit);
        dbBtn = findViewById(R.id.cpt_View);



        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        DBHelper dbHelper = new DBHelper(ChangeProjectTAActivity.this);

        Cursor cursor = new DBHelper(this).readAllCPTAData();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = cptName.getText().toString();
                String req = cptReq.getText().toString();
                String date = cptDate.getText().toString();
                String reqName = cptReqName.getText().toString();
                String Desc = cptDesc.getText().toString();
                String Reason = cptRes.getText().toString();
                String Impact = cptImpact.getSelectedItem().toString();

                String URL = Connection.API+"/create-cpat";

                if(name.isEmpty() || req.isEmpty() || date.isEmpty()|| reqName.isEmpty() || Desc.isEmpty() || Reason.isEmpty() || Impact.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Input All Fields",Toast.LENGTH_SHORT).show();

                }
                else {
                    if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                       dbHelper.insertInCPTA(name,req,date,reqName,Desc,Reason,Impact,"completed");
                        cursor.moveToFirst();
                        int id = cursor.getInt(0);
                        Log.d("ID IS :: ", String.valueOf(id));
                        Map<String, String> prams = new HashMap<String, String>();
                        prams.put("_id", String.valueOf(id));
                        prams.put("Name", name);
                        prams.put("Req", req);
                        prams.put("Date", date);
                        prams.put("ReqName", reqName);
                        prams.put("Description", Desc);
                        prams.put("Reason", Reason);
                        prams.put("Impact", Impact);
                        prams.put("status", "completed");

                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(),response.getString("Message"), Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", response.getString("Message").toString());
                                    Intent intent = new Intent(ChangeProjectTAActivity.this,AddActivity.class);
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
                            }
                        });

                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);
                    }
                    else {
                        dbHelper.insertInCPTA(name,req,date,reqName,Desc,Reason,Impact,"pending");
                        Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangeProjectTAActivity.this,AddActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
                        finish();
                    }

                }

            }
        });

    }
}