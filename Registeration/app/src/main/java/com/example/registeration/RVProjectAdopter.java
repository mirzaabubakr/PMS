package com.example.registeration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVProjectAdopter extends RecyclerView.Adapter<RVProjectAdopter.RVRrojectViewHolder> {
    Context context;
    List<projectItem> projectItemList;

    public RVProjectAdopter(List<projectItem> todoitems)
    {
        projectItemList = todoitems;
    }

    @NonNull
    @Override
    public RVRrojectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.registerationitem_layout,parent,false);

        RVRrojectViewHolder rvRrojectViewHolder = new RVRrojectViewHolder(view);
        return rvRrojectViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVRrojectViewHolder holder, int position) {
        holder.txtView.setText(projectItemList.get(position).toString());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newData = holder.txtView.getText().toString();
                int temp = projectItemList.get(position).getpID();
                Intent intent = new Intent(context, PUDActivity.class);
                intent.putExtra("pHeader","PROJECT DETAIL");
                intent.putExtra("pID", temp);
                intent.putExtra("pData", newData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectItemList.size();
    }

    public static class RVRrojectViewHolder extends RecyclerView.ViewHolder{


        TextView txtView;
        Button btn;

        public RVRrojectViewHolder(@NonNull View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.etView);
            btn = itemView.findViewById(R.id.buttonView);
        }
    }

}
