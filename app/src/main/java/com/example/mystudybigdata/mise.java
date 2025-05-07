package com.example.mystudybigdata;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class mise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText mail = findViewById(R.id.mailmise);
        EditText pwd = findViewById(R.id.pwdmise);
        EditText name = findViewById(R.id.usernamemise);
        EditText birthday = findViewById(R.id.Birthdaymise);
        Button confirm = findViewById(R.id.button);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString();
                String password = pwd.getText().toString();
                String username = name.getText().toString();
                String Birthday = birthday.getText().toString();

                int id = getIntent().getIntExtra("studentId",1);
                dbHelper.updateStudent(id,username, email, password, Birthday);
                Intent intent = new Intent(mise.this, MainActivity.class);

                startActivityForResult(intent, 1);


                finish();
            }
        });
    }

}