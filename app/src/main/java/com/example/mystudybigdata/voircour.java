package com.example.mystudybigdata;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class voircour extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voircour);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button confirm=findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sem=getSemestre();

                if(sem == 0) {Toast.makeText(voircour.this, "Please select a semester", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(sem==3){
                    Intent i = new Intent(voircour.this, voircoursem3.class);
                    int id=getIntent().getIntExtra("studentId",1);
                    String username=getIntent().getStringExtra("username");
                    i.putExtra("studentId",id);
                    i.putExtra("username",username);
                    startActivity(i);
                    finish();
                }
                if(sem==4){
                    Intent i = new Intent(voircour.this,voircoursem4.class);
                    int id=getIntent().getIntExtra("studentId",1);
                    String username=getIntent().getStringExtra("username");
                    i.putExtra("studentId",id);
                    i.putExtra("username",username);;
                    startActivity(i);
                    finish();
                }
                if(sem==5){
                    Intent i = new Intent(voircour.this,voircoursem5.class);
                    int id=getIntent().getIntExtra("studentId",1);
                    String username=getIntent().getStringExtra("username");
                    i.putExtra("studentId",id);
                    i.putExtra("username",username);

                    startActivity(i);
                    finish();
                }
            }
        });
        ImageButton retour = findViewById(R.id.homeButton);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(voircour.this,Activity2.class);
                startActivity(back);
                finish();
            }
        });




    }
    public int getSemestre(){
        int sem=0;
        RadioGroup radioGroup = findViewById(R.id.Radio); // Use your actual RadioGroup ID
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if(selectedId==R.id.radioButton){
            sem=3;
        }
        if(selectedId==R.id.radioButton2){
            sem=4;
        }
        if(selectedId==R.id.radioButton3){
            sem=5;
        }
        return sem;
    }
}