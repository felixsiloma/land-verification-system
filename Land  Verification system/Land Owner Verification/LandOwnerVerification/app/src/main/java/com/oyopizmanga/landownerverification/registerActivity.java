package com.oyopizmanga.landownerverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {
    EditText username, password, email, location,reppass;
    Button signup;
    FirebaseAuth mAuth;
    ProgressBar progress;
    DatabaseReference reference;
    FirebaseDatabase rootNode;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.pass);
        email = findViewById(R.id.email);
        location = findViewById(R.id.location);
        reppass=findViewById(R.id.repeatpass);
        signup = findViewById(R.id.signup);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameme = username.getText().toString();
                String passwordme = password.getText().toString();
                String emailme = email.getText().toString();
                String reppassme=reppass.getText().toString();
                String locationme = location.getText().toString();
                if (usernameme.isEmpty() || passwordme.isEmpty() || locationme.isEmpty() || emailme.isEmpty()) {
                    Toast.makeText(registerActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                } else if (passwordme.length() < 6) {
                    Toast.makeText(registerActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else if (!emailme.matches(emailPattern)) {
                    Toast.makeText(registerActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();

                } else if (!passwordme.matches(reppassme)) {
                    Toast.makeText(registerActivity.this, "password do not match", Toast.LENGTH_SHORT).show();
                }
                else{
                    Register(emailme, passwordme);
                    UserHelper userHelper = new UserHelper(usernameme, locationme, emailme);
                    reference.push().setValue(userHelper);
                }

            }
        });
    }


    private void Register(String emailme, String passwordme) {
        mAuth.createUserWithEmailAndPassword(emailme, passwordme).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(registerActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(registerActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(registerActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void login(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}