package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ChangePasswordActivity extends AppCompatActivity {

    EditText et1,et2,etPwd;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        DBHelper dbHelper = new DBHelper(this);


        et1 = findViewById(R.id.ch_id);
        et2 = findViewById(R.id.ch_pswd);
        etPwd = findViewById(R.id.ch_newPwd);
        btn = findViewById(R.id.chp_btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t1,t2,t3;
                t1 = et1.getText().toString();
                t2 = et2.getText().toString();
                t3 = etPwd.getText().toString();

                String URL = Connection.API +"/update-user/"+t1;
                if (t1.isEmpty() || t2.isEmpty() || t3.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Input All Fields", Toast.LENGTH_SHORT).show();
                }
                else if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                    Map<String, String> prams = new HashMap<String, String>();
                    prams.put("OldPassword", t2);
                    prams.put("NewPassword", t3);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.d("TAG", response.getString("Message"));
                                dbHelper.updatePassword(t1,t2,t3);
                                Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangePasswordActivity.this,StartActivity.class);
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
                else{
                    Toast.makeText(getApplicationContext(), "Please Connect Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}