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
import android.widget.Button;
import android.widget.EditText;
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

public class RUDActivity extends AppCompatActivity {


    EditText etname,etPh, etAddress;
    TextView etEmail;
    Button btnUpdate , btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rudactivity);

        etname = findViewById(R.id.RD_name);
        etEmail = findViewById(R.id.RD_Email);
        etPh = findViewById(R.id.RD_PhNum);
        etAddress = findViewById(R.id.RD_Address);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);

        final String[] temp = {getIntent().getExtras().getString("Email")};

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);



        etEmail.setText(temp[0]);


        DBHelper dbHelper = new DBHelper(RUDActivity.this);
        Cursor cursor = new DBHelper(this).readallRegisterData();
        while (cursor.moveToNext()) {
            String check = cursor.getString(2);
            if (check.equals(temp[0])) {

                etname.setText(cursor.getString(1));
                etEmail.setText(cursor.getString(2));
                etPh.setText(cursor.getString(3));
                etAddress.setText(cursor.getString(5));
            }
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String URL = Connection.API +"/update-user/"+temp[0];
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                    Map<String, String> prams = new HashMap<String, String>();
                    prams.put("Name", etname.getText().toString());
                    prams.put("Email", etEmail.getText().toString());
                    prams.put("Phone", etPh.getText().toString());
                    prams.put("Address", etAddress.getText().toString());
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_SHORT).show();
                                dbHelper.updateRegisterData(temp[0],etname.getText().toString(),etEmail.getText().toString(),etPh.getText().toString(),etAddress.getText().toString());
                                Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
                                temp[0] = etEmail.getText().toString();
                                Intent intent = new Intent(RUDActivity.this,AddActivity.class);
                                intent.putExtra("email",etEmail.getText().toString());
                                startActivity(intent);
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

                String URL = Connection.API +"/delete-user/"+temp[0];
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                    dbHelper.deleteSpecificRegister(temp[0]);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, URL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RUDActivity.this,StartActivity.class);
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