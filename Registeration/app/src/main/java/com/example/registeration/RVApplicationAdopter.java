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

public class RVApplicationAdopter extends RecyclerView.Adapter<RVApplicationAdopter.RVApplicationViewHolder> {

    Context context;
    List<applicationItem> applicationItemList;

    public RVApplicationAdopter(List<applicationItem> todoitems) {
        applicationItemList = todoitems;
    }

    @NonNull
    @Override
    public RVApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.registerationitem_layout,parent,false);

        RVApplicationAdopter.RVApplicationViewHolder rvApplicationViewHolder = new RVApplicationAdopter.RVApplicationViewHolder(view);
        return rvApplicationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVApplicationViewHolder holder, int position) {

        holder.txtView.setText(applicationItemList.get(position).toString());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newData = holder.txtView.getText().toString();
                int temp = applicationItemList.get(position).getId();
                Intent intent = new Intent(context, PTApplicationCRUDActivity.class);
                intent.putExtra("pID", temp);
                intent.putExtra("pData", newData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applicationItemList.size();
    }


    public static class RVApplicationViewHolder extends RecyclerView.ViewHolder {
        TextView txtView;
        Button btn;

        public RVApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.etView);
            btn = itemView.findViewById(R.id.buttonView);
        }
    }
}

