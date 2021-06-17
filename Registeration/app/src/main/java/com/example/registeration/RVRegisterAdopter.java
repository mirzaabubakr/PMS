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

public class RVRegisterAdopter extends RecyclerView.Adapter<RVRegisterAdopter.RVRegisterHolder> {

    List<registerItem> registerItemList;
    Context context;

    public RVRegisterAdopter(List<registerItem> todoitems)
    {
        registerItemList = todoitems;
    }

    @NonNull
    @Override
    public RVRegisterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(R.layout.registerationitem_layout,parent,false);
        RVRegisterAdopter.RVRegisterHolder rvRrojectViewHolder = new RVRegisterAdopter.RVRegisterHolder(view);
        return rvRrojectViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RVRegisterHolder holder, int position) {

        holder.txtView.setText(registerItemList.get(position).toString());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newData = holder.txtView.getText().toString();
                int temp = registerItemList.get(position).getID();
                Intent intent = new Intent(context, RUDActivity.class);
                intent.putExtra("RID", temp);
                intent.putExtra("RData", newData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return registerItemList.size();
    }

    public class RVRegisterHolder extends RecyclerView.ViewHolder{
        TextView txtView;
        Button btn;
        public RVRegisterHolder(@NonNull View itemView) {
            super(itemView);
            txtView = itemView.findViewById(R.id.etView);
            btn = itemView.findViewById(R.id.buttonView);
        }
    }
}
