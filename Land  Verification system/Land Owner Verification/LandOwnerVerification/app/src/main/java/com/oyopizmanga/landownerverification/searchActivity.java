package com.oyopizmanga.landownerverification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class searchActivity extends AppCompatActivity {
    ArrayList<String> arrayList = new ArrayList<>();
    DatabaseReference mref;
    SearchView searchView;
    EditText id, name, idset, locationmemeo, locationtr;
    TextView ownersay, owner, landlocation;
    FirebaseDatabase rootNode;
    Button viewonmap;
    DatabaseReference reference;
    LinearLayout linearLayout, linearLayoutbro;
    ArrayAdapter<String> myArrayadapter;
    ListView lv;
    Button search, setText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        myArrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        search = findViewById(R.id.searchmain);
        name = findViewById(R.id.name);
        linearLayout = findViewById(R.id.linearbro);
        idset = findViewById(R.id.enterid);
        linearLayoutbro = findViewById(R.id.linearbro1);
        locationmemeo = findViewById(R.id.location_memo);
        owner = findViewById(R.id.owner);
        locationtr = findViewById(R.id.locationtr);
        landlocation = findViewById(R.id.land_location);
        landlocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String mapslcn = landlocation.getText().toString();
                locationtr.setText(mapslcn);
                viewonmap.setVisibility(View.VISIBLE);
            }
        });
        ownersay = findViewById(R.id.ownersay);
        setText = findViewById(R.id.setText);
        dbsearch();
        viewonmap = findViewById(R.id.viewonmap);
        String bro = locationtr.getText().toString();
        viewonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapslcn = locationtr.getText().toString();
                //locationtr.setText(mapslcn);
                Intent intent = new Intent(v.getContext(), mapsfinalActivity.class);
                //intent.putExtra("model", model);
                intent.putExtra("mapslcn", mapslcn);
                startActivity(intent);
            }
        });
        idset.setText(getIntent().getStringExtra("idsn") + "");
        setText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("database");
        id = findViewById(R.id.id);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("database");
                myRef.orderByChild("id").equalTo(idset.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            // Log.d("TAG", "PARENT: "+ childDataSnapshot.getKey());
                            Log.d("TAG", "" + childDataSnapshot.child("name").getValue());
                            linearLayout.setVisibility(View.VISIBLE);
                            linearLayoutbro.setVisibility(View.VISIBLE);
                            Toast.makeText(searchActivity.this, "Id Owner is " + childDataSnapshot.child("name").getValue(), Toast.LENGTH_SHORT).show();
                            String ownerme = (String) childDataSnapshot.child("name").getValue();
                            String locationme = (String) childDataSnapshot.child("location").getValue();
                            owner.setText(ownerme);
                            landlocation.setText(locationme);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void dbsearch() {

    }

    public void upload(View view) {
        String idme = id.getText().toString();
        String nameme = name.getText().toString();
        String locationme = locationmemeo.getText().toString();
        Db db = new Db(nameme, idme, locationme);
        reference.push().setValue(db);
    }

}