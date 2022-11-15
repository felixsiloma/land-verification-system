package com.oyopizmanga.landownerverification;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class LandAdapter extends FirebaseRecyclerAdapter<Lands, LandAdapter.landsViewholder> {
    public LandAdapter(@NonNull FirebaseRecyclerOptions<Lands> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull landsViewholder holder, int position, @NonNull Lands model) {
        holder.location.setText(model.getLocaton());
        holder.size.setText(model.getSize());
        holder.email.setText(model.getEmail());
        holder.price.setText(model.getPrice());
        holder.url.setText(model.getUrl());
        String locationsn = (String) holder.location.getText();
        String sizesn = (String) holder.size.getText();
        String emailsn = (String) holder.email.getText();
        String pricesn = (String) holder.price.getText();
        String urlsn = (String) holder.url.getText();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), detailsActivity.class);
                //intent.putExtra("model", model);
                intent.putExtra("locationsn", locationsn);
                intent.putExtra("sizesn", sizesn);
                intent.putExtra("emailsn", emailsn);
                intent.putExtra("pricesn", pricesn);
                intent.putExtra("urlsn", urlsn);
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public landsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lands, parent, false);
        return new LandAdapter.landsViewholder(view);
    }

    public class landsViewholder extends RecyclerView.ViewHolder {
        TextView location, size, email, price, url;
        ImageView imageView;

        public landsViewholder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location_adapt);
            size = itemView.findViewById(R.id.size_adapt);
            email = itemView.findViewById(R.id.email_adapt);
            url = itemView.findViewById(R.id.url_adapt);
            imageView = itemView.findViewById(R.id.img);
            price = itemView.findViewById(R.id.price_adapt);
        }
    }
}


