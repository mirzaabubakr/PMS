package com.example.registeration;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {




    RecyclerView lvReg,lvReg1,lvReg2;
    RecyclerView.LayoutManager layoutManager,layoutManager1,layoutManager2;
    List<projectItem> pRegData = new ArrayList<>();
    List<applicationItem> aRegData = new ArrayList<>();
    List<resourceItem> rsRegData = new ArrayList<>();
    Button dbRbtn;

    RVProjectAdopter rvProjectAdopter;
    RVResourcesAdopter rvResourcesAdopter;
    RVApplicationAdopter rvApplicationAdopter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String check = getIntent().getExtras().getString("ListView");
        Toast.makeText(getApplicationContext(),check,Toast.LENGTH_SHORT).show();
        dbRbtn = findViewById(R.id.dbDBtn);
        DBHelper dbHelper = new DBHelper(this);


        if(check.equals("Projects"))
        {
            lvReg = findViewById(R.id.projectRVList);
            layoutManager = new LinearLayoutManager(this);
            lvReg.setLayoutManager(layoutManager);
            rvProjectAdopter = new RVProjectAdopter(pRegData);
            lvReg.setAdapter(rvProjectAdopter);

            Cursor cursor1 = new DBHelper(this).readAllProjectData();
            while (cursor1.moveToNext()) {
                //   Log.d("TAG", cursor.toString());

                pRegData.add(new projectItem(cursor1.getInt(0), cursor1.getString(1), cursor1.getString(2), cursor1.getString(3), cursor1.getString(4),cursor1.getString(5)));
            }

        }
        else if(check.equals("Resources")){
            lvReg2 = findViewById(R.id.projectRVList);
            layoutManager2 = new LinearLayoutManager(this);
            lvReg2.setLayoutManager(layoutManager2);
            rvResourcesAdopter = new RVResourcesAdopter(rsRegData);
            lvReg2.setAdapter(rvResourcesAdopter);
            Cursor cursor2 = new DBHelper(this).readAllResourceData();
            while (cursor2.moveToNext()) {
                //   Log.d("TAG", cursor.toString());

                rsRegData.add(new resourceItem(cursor2.getInt(0), cursor2.getString(1), cursor2.getString(2), cursor2.getString(3), cursor2.getString(4)));
            }

        }
        else {
            lvReg1 = findViewById(R.id.projectRVList);
            layoutManager1 = new LinearLayoutManager(this);
            lvReg1.setLayoutManager(layoutManager1);
            rvApplicationAdopter = new RVApplicationAdopter(aRegData);
           lvReg1.setAdapter(rvApplicationAdopter);
            Cursor cursor = new DBHelper(this).readAllCPTAData();
            while (cursor.moveToNext()) {
                //   Log.d("TAG", cursor.toString());
                aRegData.add(new applicationItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),cursor.getString(6), cursor.getString(7)));
            }
        }
        dbRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this,ViewDBActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}