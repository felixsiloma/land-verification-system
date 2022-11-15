package com.oyopizmanga.landownerverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.emaillogin);
        check();
        password = findViewById(R.id.passlogin);
        login = findViewById(R.id.loginbtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailme = email.getText().toString();
                String passwordme = password.getText().toString();
                if (emailme.isEmpty() || passwordme.isEmpty()) {
                    Toast.makeText(MainActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                } else if (passwordme.length() < 6) {
                    Toast.makeText(MainActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else if (!emailme.matches(emailPattern)) {
                    Toast.makeText(MainActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                } else {
                    Loginme(emailme, passwordme);
                }
            }
        });
    }

    private void check() {
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, home2Activity.class));
        } else {
            Toast.makeText(this, "No User detected", Toast.LENGTH_SHORT).show();
        }
    }

    private void Loginme(String emailme, String passwordme) {
        mAuth.signInWithEmailAndPassword(emailme, passwordme).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, home2Activity.class));
                    Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void register(View view) {
        startActivity(new Intent(this, registerActivity.class));
    }
}