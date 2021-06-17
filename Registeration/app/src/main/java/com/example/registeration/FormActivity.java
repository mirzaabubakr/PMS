package com.example.registeration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormActivity extends AppCompatActivity {


    EditText editText1,editText2,editText3,editText4,editText5;
    Button submit;
    List<registerItem> regItemList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        editText1 = findViewById(R.id.Name);
        editText2 = findViewById(R.id.etID);
        editText3 = findViewById(R.id.etPhNumber);
        editText4 = findViewById(R.id.etAddress);
        editText5 = findViewById(R.id.etPassword);
        submit = findViewById(R.id.btSubmit);

        String URL = Connection.API +"/register";

        DBHelper dbHelper = new DBHelper(FormActivity.this);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                    String name = editText1.getText().toString();
                    String email = editText2.getText().toString();
                    String phNum = editText3.getText().toString();
                    String password = editText5.getText().toString();
                    String address;
                    address = editText4.getText().toString();
                    if(name.isEmpty() || email.isEmpty() || phNum.isEmpty() || password.isEmpty() || address.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Please Input All Fields",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Map<String, String> prams = new HashMap<String, String>();
                        prams.put("Name", name);
                        prams.put("Email", email);
                        prams.put("PhoneNumber", phNum);
                        prams.put("Password", password);
                        prams.put("Address", address);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Toast.makeText(getApplicationContext(),response.getString("Message"), Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", response.getString("Message").toString());
                                    dbHelper.insertInRegister(name, email, phNum, password, address);
                                    writeData();
                                    Intent intent = new Intent(FormActivity.this,StartActivity.class);
                                    {
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
                                Toast.makeText(getApplicationContext(), "Account Already Exists", Toast.LENGTH_SHORT).show();
                                Log.d("TAG", error.toString());
                            }
                        });

                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        queue.add(request);
                    }
            }
        });

    }



    void writeData() {

        File extSDIR = Environment.getExternalStorageDirectory();
        File myDataDIR = new File(extSDIR,"/RegisterFile");
        if(!myDataDIR.exists())
        {
            myDataDIR.mkdir();
        }

        File myDataFile = new File(myDataDIR,"RegisterData.txt");
        try {

            FileOutputStream fOS = new FileOutputStream(new File(String.valueOf(myDataFile)), true );


            for(registerItem item:regItemList)
            {
                fOS.write(item.toString().getBytes());
                fOS.write("\n".getBytes());
            }

            fOS.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}