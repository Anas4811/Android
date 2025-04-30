package com.example.mystudybigdata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button loginButton=findViewById(R.id.buttonLogin);
        EditText pwd = findViewById(R.id.editTextPassword);
        EditText Username= findViewById(R.id.editTextUsername);
        Button signinButton=findViewById(R.id.signin);
        DatabaseHelper  dbHelper=new DatabaseHelper( getApplicationContext());
        loginButton.setOnClickListener(v -> {
            String username = Username.getText().toString().trim();
            String password = pwd.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int studentId = dbHelper.getStudentId(username, password);
            if (studentId != -1) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, Activity2.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("studentId", studentId);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ActivitySignIn.class);
                startActivity(i);
                finish();

            }
        });

    }
}