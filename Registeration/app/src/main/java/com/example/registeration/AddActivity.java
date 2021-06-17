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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class AddActivity extends AppCompatActivity {

    Button pBtn,resBtn,cpaBtn;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        pBtn = findViewById(R.id.projectBtn);
        resBtn = findViewById(R.id.resourceBtn);
        cpaBtn = findViewById(R.id.cptaBtn);

        email = getIntent().getExtras().getString("email");
        DBHelper dbHelper = new DBHelper(AddActivity.this);



        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            Cursor cursor = new DBHelper(this).readAllProjectData();
            Cursor cursor1 = new DBHelper(this).readAllResourceData();
            Cursor curser2 = new DBHelper(this).readAllCPTAData();
            UpdatePending(dbHelper, cursor);
            UpdatePendingResource(dbHelper,cursor1);
            UpdatePendingApplications(dbHelper,curser2);
        }



        pBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,ProjectActivity.class);
                {
                    intent.putExtra("temp",email);
                    startActivity(intent);
                }
            }
        });

        resBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,ResourcesActivity.class);
                {
                    intent.putExtra("temp",email);
                    startActivity(intent);
                }
            }
        });

        cpaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this,ChangeProjectTAActivity.class);
                {
                    intent.putExtra("temp",email);
                    startActivity(intent);
                }
            }
        });
    }

    private  void UpdatePendingApplications(DBHelper dbHelper,Cursor cursor){
        while (cursor.moveToNext()) {
            String sync_status = cursor.getString(8);
            if (sync_status.equals("pending")) {
                String URL = Connection.API + "/create-cpat";
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String req = cursor.getString(2);
                String date = cursor.getString(3);
                String reqName = cursor.getString(4);
                String Desc = cursor.getString(5);
                String Reason = cursor.getString(6);
                String Impact = cursor.getString(7);

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

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.getString("Message");
                            if (res.equals("Cpat Created Successfully")){
                                dbHelper.updateCPTAData(id,name,req,date,reqName,Desc,Reason,Impact,"completed");
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
        }
    }

    private void UpdatePendingResource(DBHelper dbHelper,Cursor cursor){
        while (cursor.moveToNext()) {
            String sync_status = cursor.getString(5);
            Log.d("TAG", "Response"+sync_status);
            if (sync_status.equals("pending")) {
                String URL = Connection.API + "/create-resource";
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String type = cursor.getString(2);
                String pStatus = cursor.getString(3);
                String desc = cursor.getString(4);

                Map<String, String> prams = new HashMap<String, String>();
                prams.put("_id", String.valueOf(id));
                prams.put("rName", name);
                prams.put("rType", type);
                prams.put("rStatus", pStatus);
                prams.put("rDescription", desc);
                prams.put("status", "completed");

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.getString("Message");
                            if (res.equals("Resource Created Successfully")){
                                dbHelper.updateResourceData(Integer.valueOf(id),name,type,pStatus,desc,"completed");
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
        }

    }
    private void UpdatePending(DBHelper dbHelper, Cursor cursor) {
        while (cursor.moveToNext()) {
            String sync_status = cursor.getString(5);
            if (sync_status.equals("pending")) {
                String URL = Connection.API + "/create-project";
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String type = cursor.getString(2);
                String pStatus = cursor.getString(3);
                String desc = cursor.getString(4);

                Map<String, String> prams = new HashMap<String, String>();
                prams.put("_id", String.valueOf(id));
                prams.put("pName", name);
                prams.put("pType", type);
                prams.put("pStatus", pStatus);
                prams.put("pDescription", desc);
                prams.put("status", "completed");

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL, new JSONObject(prams), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String res = response.getString("Message");
                            if (res.equals("Project Created Successfully")){
                                dbHelper.updateProjectData(Integer.valueOf(id),name,type,pStatus,desc,"completed");
                                //          context.sendBroadcast(new Intent(Connection.Update));
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.addmanu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_gen:

                Intent intent2 = new Intent(AddActivity.this, RUDActivity.class);
                intent2.putExtra("Email",email);
                startActivity(intent2);
            break;
            case R.id.menu_view:
                Intent intent = new Intent(AddActivity.this, ViewDBActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_signout:
                Intent intent1 = new Intent(AddActivity.this, StartActivity.class);
                startActivity(intent1);
                finish();
                break;
            default:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}