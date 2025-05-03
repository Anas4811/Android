package com.example.mystudybigdata;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent2 = getIntent();  // Get the intent from the previous activity
        String username = intent2.getStringExtra("username");  // Retrieve the username
        String password = intent2.getStringExtra("password");
        int id=intent2.getIntExtra("studentId",-1);
        Toast.makeText(getApplicationContext(),"Bienvenue : "+username,Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton Retour=findViewById(R.id.home);
        Retour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i=new Intent(Activity2.this, MainActivity.class);
                startActivity(i);
                finish();


            }
        });
        ImageButton Calculer=findViewById(R.id.Calc);
        Calculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calc=new Intent(Activity2.this,CalculerMoyenne.class);
                startActivity(calc);

                finish();
            }
        });
        ImageButton calendrier =findViewById(R.id.imageButton9);
        Intent intent = getIntent(); // Get the Intent that started Activity2

// Retrieve the extras passed from MainActivity


        calendrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new Intent to navigate to 'calendrier' activity
                Intent calendar = new Intent(Activity2.this, calendrier.class);

                // Add extras to the Intent (passing username and password)
                calendar.putExtra("username",username);  // Pass username from Activity2
                calendar.putExtra("password",password);  // Pass password from Activity2
                calendar.putExtra("studentId",id);
                // Start the 'calendrier' activity
                startActivity(calendar);

                // Optionally, finish Activity2 to go back to the previous activity
                finish();
            }
        });

    }
}