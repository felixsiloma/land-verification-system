package com.oyopizmanga.landownerverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class myadsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    MyadsAdapter adapter;
    FirebaseUser user;
    DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_myads);
        recyclerView = findViewById(R.id.myrecycler);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mbase = FirebaseDatabase.getInstance().getReference("myLandAds").child(uid);
        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Myads> options = new FirebaseRecyclerOptions.Builder<Myads>().setQuery(mbase, Myads.class).build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new MyadsAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}