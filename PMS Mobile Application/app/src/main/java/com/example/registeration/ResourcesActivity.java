package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

public class ResourcesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText etName,etType,etDesc;
        Spinner spinner;
        Button addBtn;
        ImageButton dbBtn;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        String email = getIntent().getExtras().getString("temp");

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        String URL = Connection.API + "/create-resource";

        etName = findViewById(R.id.R_name);
        etType = findViewById(R.id.R_Type);
        spinner = (Spinner) findViewById(R.id.R_spinner);
        etDesc = findViewById(R.id.R_Desc);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.R_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        addBtn = findViewById(R.id.R_addBtn);
        dbBtn = findViewById(R.id.RdbView);

        DBHelper dbHelper = new DBHelper(ResourcesActivity.this);
        Cursor cursor = new DBHelper(this).readAllResourceData();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String type = etType.getText().toString();
                String status = spinner.getSelectedItem().toString();
                String desc = etDesc.getText().toString();
                if(name.isEmpty() || type.isEmpty() || status.isEmpty()|| desc.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Input All Fields",Toast.LENGTH_SHORT).show();

                }
                else if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                    dbHelper.insertInResource(name,type,status,desc,"completed");
                   cursor.moveToFirst();
                    int id = cursor.getInt(0);
                    Map<String, String> prams = new HashMap<String, String>();
                    prams.put("_id", String.valueOf(id));
                    prams.put("rName", name);
                    prams.put("rType", type);
                    prams.put("rStatus", status);
                    prams.put("rDescription", desc);
                    prams.put("status", "completed");

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Toast.makeText(getApplicationContext(),response.getString("Message"), Toast.LENGTH_SHORT).show();
                                Log.d("TAG", response.getString("Message").toString());
                                Intent intent = new Intent(ResourcesActivity.this,AddActivity.class);
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
                    dbHelper.insertInResource(name,type,status,desc,"pending");
                    Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResourcesActivity.this,AddActivity.class);
                    intent.putExtra("email",email);
                    startActivity(intent);
                    finish();
                }
            }
        });

        dbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ResourcesActivity.this,ViewDBActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}