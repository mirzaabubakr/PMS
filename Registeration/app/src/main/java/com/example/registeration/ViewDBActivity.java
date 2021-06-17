package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class ViewDBActivity extends AppCompatActivity {

    Button BtnP,BtnR,BtnA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dbactivity);

        BtnP = findViewById(R.id.btn_Projects);
        BtnR = findViewById(R.id.btn_Resources);
        BtnA = findViewById(R.id.btn_Applications);

        BtnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDBActivity.this, ListActivity.class);
                {
                    intent.putExtra("ListView","Projects");
                    startActivity(intent);
                }
            }
        });

        BtnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDBActivity.this, ListActivity.class);
                {
                    intent.putExtra("ListView","Resources");
                    startActivity(intent);
                }
            }
        });

        BtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewDBActivity.this, ListActivity.class);
                {
                    intent.putExtra("ListView","Applications");
                    startActivity(intent);
                }
            }
        });
    }
}