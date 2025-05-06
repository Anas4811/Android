package com.example.mystudybigdata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
        professors.add(new Professor("Salma Smaoui", "salma.smaoui@esct.uma.tn", "Java"));
        professors.add(new Professor("Imen Ouerghi", "imen.ouerghi@esct.uma.tn", "Proba"));
        professors.add(new Professor("Ahmed Ben Hammouda", "ahmed.benhamouda@esct.uma.tn", "Java TP, PL/SQl,entrepot"));
        professors.add(new Professor("Houda Fekih", "houda.fekih@esct.uma.tn", "Conception UML"));
        professors.add(new Professor("Dalila Tayachi", "Dalila.tayachi@esct.uma.tn", "Graphe"));
        professors.add(new Professor("Olfa Belkahla", "olfa.belkahla@esct.uma.tn", "TLA"));
        professors.add(new Professor("Olfa Benahmed", "olfa.benahmed@isamm.uma.tn", "Reseaux"));
        professors.add(new Professor("Amel Sehli", "amel.sehli@isamm.uma.tn", "Services Reseaux"));
        professors.add(new Professor("Hela Sassi", "hela.sassi@isamm.uma.tn", "Gestion d'entreprise / Entrepreunariat"));
        professors.add(new Professor("Mayssa Trabelsi", "mayssa.trabelsi@fst.utm.tn", "Systémes Distribués "));
        professors.add(new Professor("Synda El Ghoul", "sinda.elghoul2@gmail.com", "Cloud"));
        professors.add(new Professor("Malek Ghenima", "malek.ghenima@gmail.com", "Gestion Informatisée"));
        professors.add(new Professor("Imen Ben Fradj", "imen.benfraj@esct.uma.tn", "P.Web"));
        professors.add(new Professor("Nader Kolsi", "nader.kolsi@esct.uma.tn", "Data Mining"));
        professors.add(new Professor("Chedi Becheikh-Ali", "chedi.bechikh@gmail.com", "Programmation Mobile"));
        professors.add(new Professor("Slim Hamrouni", "maitreslim@yahoo.fr", "Droit Informatisé"));
        professors.add(new Professor("Rebah Jlassi", "rebah.jlassi@esct.uma.tn", "Statistiques"));
        professors.add(new Professor("Mohamed Hmiden", "mohamedhmiden@gmail.com", "Big DATA"));
        professors.add(new Professor("Mayssa Madani", "", "Big DATA TP"));
        professors.add(new Professor("Enjie Ghorbel", "enjie.ghorbel@isamm.uma.tn", "AI"));
        professors.add(new Professor("Wafa Najjar", "wafa.najjar@esct.uma.tn", "Cloud/Securité"));
        professors.add(new Professor("Mehdi Ghanem", "mehdighanem@gmail.com", "Projet Fédéré"));
        professors.add(new Professor("T. Saamali", "", "Technique de Prevision"));
        professors.add(new Professor("M. Chaabani", "", "Visualisation des Données"));
        professors.add(new Professor("T. Yefreni", "", "Base de Données NoSQL"));
        professors.add(new Professor("Wala Rabhi", "", "Technique de Veille"));
        professors.add(new Professor("M. Ayari", "", "Technique de Prévision TP"));
        professors.add(new Professor("T. Hadded", "", "Framework WEB"));
        professors.add(new Professor("S. Lasmar", "", "ERP&Personnalisation"));


        MyAdapter adapter = new MyAdapter(professors);
        recyclerView.setAdapter(adapter);
        ImageButton home=findViewById(R.id.home7);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeretour= new Intent(listeprof.this,Activity2.class);
                int id = getIntent().getIntExtra("studentId",1);
                String username = getIntent().getStringExtra("username");
                homeretour.putExtra("studentId",id);
                homeretour.putExtra("username",username);
                startActivity(homeretour);
                finish();
            }
        });
    }


}