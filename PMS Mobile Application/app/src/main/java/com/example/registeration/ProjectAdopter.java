package com.example.registeration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProjectAdopter extends BaseAdapter {

    List<projectItem> projectItemList;
    Context context;

    public ProjectAdopter(List<projectItem> list) {
        projectItemList = list;
    }

    @Override
    public int getCount() {
        return projectItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return projectItemList.get(position);
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
        ((TextView) view.findViewById(R.id.etView)).setText(projectItemList.get(position).toString());

        return view;
    }
}
