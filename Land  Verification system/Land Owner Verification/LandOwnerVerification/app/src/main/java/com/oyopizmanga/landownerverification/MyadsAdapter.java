package com.oyopizmanga.landownerverification;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyadsAdapter extends FirebaseRecyclerAdapter<Myads, MyadsAdapter.myadsViewholder> {
    public MyadsAdapter(@NonNull FirebaseRecyclerOptions<Myads> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyadsAdapter.myadsViewholder holder, int position, @NonNull Myads model) {
        holder.location.setText(model.getLocaton());
        holder.size.setText(model.getSize());
        holder.email.setText(model.getEmail());
        holder.price.setText(model.getPrice());
        String mail= (String) holder.email.getText();

    }

    @NonNull
    @Override
    public MyadsAdapter.myadsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lands, parent, false);
        return new MyadsAdapter.myadsViewholder(view);
    }

    public class myadsViewholder extends RecyclerView.ViewHolder {
        TextView location,size,email,price;
        public myadsViewholder(@NonNull View itemView) {
            super(itemView);
            location=itemView.findViewById(R.id.location_adapt);
            size=itemView.findViewById(R.id.size_adapt);
            email=itemView.findViewById(R.id.email_adapt);
            price=itemView.findViewById(R.id.price_adapt);
        }
    }
}

