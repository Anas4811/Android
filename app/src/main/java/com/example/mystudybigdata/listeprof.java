package com.example.mystudybigdata;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class listeprof extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listeprof);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        int numberOfColumns = 2; // or 3 based on screen size
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        List<Professor> professors = new ArrayList<>();
        //pour malek a modifier
        professors.add(new Professor("Dr. John Smith", "john.smith@university.edu",""));
        professors.add(new Professor("Prof. Jane Doe", "jane.doe@university.edu",""));
// Add more...

        MyAdapter adapter = new MyAdapter(professors);
        recyclerView.setAdapter(adapter);
    }


}