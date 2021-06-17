package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
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

public class PTApplicationCRUDActivity extends AppCompatActivity {

    EditText cptName,cptReq,cptDate,cptReqName,cptDesc,cptRes;
    Spinner cptImpact;
    Button btnUpdate,btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptapplication_crudactivity);

        cptName = findViewById(R.id.CAT_name);
        cptReq = findViewById(R.id.CAT_req);
        cptDate = findViewById(R.id.CAT_date);
        cptReqName = findViewById(R.id.CAT_reqName);
        cptDesc = findViewById(R.id.CAT_chngDesc);
        cptRes = findViewById(R.id.CAT_reason);
        cptImpact = (Spinner) findViewById(R.id.CAT_impact);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cpta_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cptImpact.setAdapter(adapter);

        btnUpdate = findViewById(R.id.btn_Update);
        btnDelete = findViewById(R.id.btn_delete);

        int id = getIntent().getExtras().getInt("pID");
        String data = getIntent().getExtras().getString("pData");

        String[] infos = data.split("/");
        cptName.setText(infos[0]);
        cptReq.setText(infos[1]);
        cptDate.setText(infos[2]);
        cptReqName.setText(infos[3]);
        cptDesc.setText(infos[4]);
        cptRes.setText(infos[5]);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        DBHelper dbHelper = new DBHelper(PTApplicationCRUDActivity.this);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = Connection.API +"/update-cpat/"+id;
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                    Map<String, String> prams = new HashMap<String, String>();

                    prams.put("Name", cptName.getText().toString());
                    prams.put("Req", cptReq.getText().toString());
                    prams.put("Date", cptDate.toString());
                    prams.put("ReqName", cptReqName.getText().toString());
                    prams.put("Description", cptDesc.getText().toString());
                    prams.put("Reason", cptRes.getText().toString());
                    prams.put("Impact", cptImpact.getSelectedItem().toString());
                    prams.put("status","completed");
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_SHORT).show();
                                dbHelper.updateCPTAData(id,cptName.getText().toString(),cptReq.getText().toString(),cptDate.getText().toString(),cptReqName.getText().toString(),cptDesc.getText().toString(),cptRes.getText().toString(),cptImpact.getSelectedItem().toString(),"completed");
                                Intent intent = new Intent(PTApplicationCRUDActivity.this,ViewDBActivity.class);
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
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = Connection.API +"/delete-cpat/"+id;
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                    dbHelper.deleteSpecificCPTA(id);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, URL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PTApplicationCRUDActivity.this,ViewDBActivity.class);
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
        });
    }
}