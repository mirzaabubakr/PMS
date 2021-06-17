package com.example.registeration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    Button btn, btnLogin;
    EditText etEmail, etPassword;
    RegisterAdopter registerAdopter;




    List<registerItem> regData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btnLogin);
        btn = findViewById(R.id.btRegister);


        DBHelper dbHelper = new DBHelper(StartActivity.this);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        String URL = Connection.API + "/login";
    //    readData();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String st1 = etEmail.getText().toString();
                String st2 = etPassword.getText().toString();

                if (st1.isEmpty() || st2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Input All Fields", Toast.LENGTH_SHORT).show();
                } else if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    Map<String, String> prams = new HashMap<String, String>();
                    prams.put("Email", st1);
                    prams.put("Password", st2);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(StartActivity.this, AddActivity.class);
                                {
                                    intent.putExtra("email",st1);
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
                } else {
                    Boolean check = dbHelper.getSpecificRegisterData(st1, st2);
                    Log.d("check", check.toString());
                    if (check.equals(true)) {
                        Toast.makeText(getApplicationContext(), "User Found", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StartActivity.this, AddActivity.class);
                        {
                            intent.putExtra("email",st1);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartActivity.this, FormActivity.class);
                {
                    startActivity(intent);
                    finish();

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.startmanu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.chPassword:
                Intent intent = new Intent(StartActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.ch_exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }




           /* private void readData() {
                File extSDIR = Environment.getExternalStorageDirectory();
                File myDataDIR = new File(extSDIR, "/RegisterFile");
                if (myDataDIR.exists()) {

                    File myDataFile = new File(myDataDIR, "RegisterData.txt");
                    try {

                        FileInputStream fIS = new FileInputStream(myDataFile);

                        byte[] Bytes = new byte[1024];

                        StringBuffer stBuffer;
                        stBuffer = new StringBuffer();
                        int nBytesRead = 0;
                        do {
                            nBytesRead = fIS.read(Bytes);
                            stBuffer.append(new String(Bytes));

                        } while (nBytesRead == -1);

                        String stringData = stBuffer.toString();
                        String[] stringItem = stringData.split("\n");
                        for (String str : stringItem) {
                            if (str.contains("/"))
                                regData.add(new registerItem(str));

                        }
                        registerAdopter.notifyDataSetChanged();
                        fIS.close();

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


    }*/

}
