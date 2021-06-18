package com.example.registeration;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RegisterAdopter extends BaseAdapter {

    List<registerItem> registerItemslist;
    Context context;

    public RegisterAdopter(List<registerItem> list) {
        registerItemslist = list;
    }

    @Override
    public int getCount() {
        return registerItemslist.size();
    }

    @Override
    public Object getItem(int position) {
        return registerItemslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.registerationitem_layout, parent, false);
        Log.i("RegisterAdopter", "Item no." + position);

        ((TextView) view.findViewById(R.id.etView)).setText(registerItemslist.get(position).toString());
        String oldData = ((TextView) view.findViewById(R.id.etView)).getText().toString();

        context = parent.getContext();
        view.findViewById(R.id.buttonView).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String newData = ((TextView) view.findViewById(R.id.etView)).getText().toString();
                modifyFile(oldData, newData);


                if(registerItemslist.get(position).Header == "Register"){

                    int temp = registerItemslist.get(position).getID();
                    Intent intent = new Intent(context, RUDActivity.class);
                    intent.putExtra("RID", temp);
                    intent.putExtra("RData", newData);
                    context.startActivity(intent);
                }
                else {
                    Log.d("TAG", " No Update Pressed");
                }

            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void modifyFile(String oldString, String newString) {
        File extSDIR = Environment.getExternalStorageDirectory();
        File myDataDIR = new File(extSDIR, "/RegisterFile");
        if (myDataDIR.exists()) {

            File myDataFile = new File(myDataDIR, "RegisterData.txt");
            File fileToBeModified = new File(String.valueOf(myDataFile));

            StringBuilder oldContent = new StringBuilder();
            BufferedReader reader = null;

            FileWriter writer = null;

            try {
                reader = new BufferedReader(new FileReader(fileToBeModified));

                //Reading all the lines of input text file into oldContent

                String line = reader.readLine();

                while (line != null) {
                    oldContent.append(line).append(System.lineSeparator());

                    line = reader.readLine();
                }

                //Replacing oldString with newString in the oldContent

                String newContent = oldContent.toString().replaceAll(oldString, newString);

                //Rewriting the input text file with newContent

                writer = new FileWriter(fileToBeModified);

                writer.write(newContent);

                reader.close();
                writer.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}