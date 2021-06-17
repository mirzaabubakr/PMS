package com.example.registeration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVResourcesAdopter extends RecyclerView.Adapter<RVResourcesAdopter.RVResourceHolder> {
    List<resourceItem> resourceItemList;
    Context context;

    public RVResourcesAdopter(List<resourceItem> todoitems)
    {
        resourceItemList = todoitems;
    }

    @NonNull
    @Override
    public RVResourceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.registerationitem_layout,parent,false);
        RVResourcesAdopter.RVResourceHolder rvResourceHolder = new RVResourcesAdopter.RVResourceHolder(view);
        return rvResourceHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVResourceHolder holder, int position) {
        holder.txtView.setText(resourceItemList.get(position).toString());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newData = holder.txtView.getText().toString();
                int temp = resourceItemList.get(position).getrID();
                Intent intent = new Intent(context, PUDActivity.class);
                intent.putExtra("pHeader","RESOURCE DETAILS");
                intent.putExtra("pID", temp);
                intent.putExtra("pData", newData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resourceItemList.size();
    }

    public class RVResourceHolder extends RecyclerView.ViewHolder{
        TextView txtView;
        Button btn;
        public RVResourceHolder(@NonNull View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.etView);
            btn = itemView.findViewById(R.id.buttonView);
        }
    }
}
