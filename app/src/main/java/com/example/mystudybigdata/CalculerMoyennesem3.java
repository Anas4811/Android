package com.example.mystudybigdata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculerMoyennesem3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculer_moyennesem3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText[] MatieresRMDS = new EditText[7];
        EditText[] MatieresRMExam = new EditText[7];
        EditText[] MatieresCCDS1 = new EditText[4];
        EditText[] MatieresCCDS2 = new EditText[4];
        EditText[] MatieresCCcc = new EditText[4];

        // Initialize your EditTexts
        MatieresRMDS[0] = findViewById(R.id.dsproba);
        MatieresRMDS[1] = findViewById(R.id.dstla);
        MatieresRMDS[2] = findViewById(R.id.dsgraph);
        MatieresRMDS[3] = findViewById(R.id.dsuml);
        MatieresRMDS[4] = findViewById(R.id.dsjava);
        MatieresRMDS[5] = findViewById(R.id.dsbd);
        MatieresRMDS[6] = findViewById(R.id.dsreseau);

        MatieresRMExam[0] = findViewById(R.id.examenproba);
        MatieresRMExam[1] = findViewById(R.id.examentla);
        MatieresRMExam[2] = findViewById(R.id.examengraph);
        MatieresRMExam[3] = findViewById(R.id.examenuml);
        MatieresRMExam[4] = findViewById(R.id.examenjava);
        MatieresRMExam[5] = findViewById(R.id.examenbd);
        MatieresRMExam[6] = findViewById(R.id.examenreseau);

        MatieresCCDS1[0] = findViewById(R.id.ds1Anglais);
        MatieresCCDS1[1] = findViewById(R.id.ds1Gestion);
        MatieresCCDS1[2] = findViewById(R.id.ds1Sysdis);
        MatieresCCDS1[3] = findViewById(R.id.ds1Cloud);

        MatieresCCDS2[0] = findViewById(R.id.ds2Anglais);
        MatieresCCDS2[1] = findViewById(R.id.ds2Gestion);
        MatieresCCDS2[2] = findViewById(R.id.ds2Sysdis);
        MatieresCCDS2[3] = findViewById(R.id.ds2Cloud);

        MatieresCCcc[0] = findViewById(R.id.ccAnglais);
        MatieresCCcc[1] = findViewById(R.id.ccGestion);
        MatieresCCcc[2] = findViewById(R.id.ccSysdis);
        MatieresCCcc[3] = findViewById(R.id.ccCloud);

        Double[] coef = {2.0,1.0,1.0,1.5,2.0,1.5,1.0,1.0,1.0,1.5,1.5};

        Button b1 = findViewById(R.id.buttonsem3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double[] moyennes = new Double[11];
                boolean hasError = false;

                // Calculate the first 7 subjects
                for (int i = 0; i < 7; i++) {
                    if (MatieresRMDS[i].getText().toString().isEmpty() || MatieresRMExam[i].getText().toString().isEmpty()) {
                        hasError = true;
                        break;
                    } else {
                        Double noteDS = Double.valueOf(MatieresRMDS[i].getText().toString().trim());
                        Double noteExam = Double.valueOf(MatieresRMExam[i].getText().toString().trim());
                        if(noteExam>20||noteDS>20){
                            Toast.makeText(getApplicationContext(),"Entrer Un nombre positif inferieure ou egale à 20",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            moyennes[i] = ((noteDS * 0.3 )+ (noteExam * 0.7));

                        }
                    }
                }

                // Calculate the 4 CC subjects
                for (int i = 0; i < 4; i++) {
                    if (MatieresCCDS1[i].getText().toString().isEmpty() || MatieresCCDS2[i].getText().toString().isEmpty() || MatieresCCcc[i].getText().toString().isEmpty()) {
                        hasError = true;
                        break;
                    } else {

                        Double noteDS1 = Double.valueOf(MatieresCCDS1[i].getText().toString().trim());
                        Double noteDS2 = Double.valueOf(MatieresCCDS2[i].getText().toString().trim());
                        Double notecc = Double.valueOf(MatieresCCcc[i].getText().toString().trim());
                        if(noteDS1>20 || noteDS2>20 && notecc>20){
                            Toast.makeText(getApplicationContext(),"Entrer Un nombre positif inferieure ou egale à 20",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            moyennes[i+7] = ((noteDS1 * 0.4 )+ (noteDS2 * 0.4 )+( notecc * 0.2));

                        }
                    }
                }

                if (hasError) {
                    Toast.makeText(getApplicationContext(), "Make sure no fields are empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double total = 0.0;
                for (int i = 0; i < 11; i++) {
                    if (moyennes[i] != null) {
                        total += moyennes[i] * coef[i];
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Il existe une valuer nulle",Toast.LENGTH_SHORT).show();

                    }
                }
                Double moy = total / 15;

                if (moy >= 10.0) {
                    Bundle bundle = new Bundle();
                    bundle.putDouble("result", moy); // send the real calculated moyenne

                    FragmentValidée fragment = new FragmentValidée();
                    fragment.setArguments(bundle);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            .replace(R.id.fragmentContainer, fragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putDouble("result", moy); // send the real calculated moyenne

                    FragmentNonValide fragment = new FragmentNonValide();
                    fragment.setArguments(bundle);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                            .replace(R.id.fragmentContainer, fragment)
                            .addToBackStack(null)
                            .commit();
                    }
            }
        });

        ImageButton retour = findViewById(R.id.home8);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(CalculerMoyennesem3.this,Activity2.class);
                int id=getIntent().getIntExtra("studentId",1);
                String username=getIntent().getStringExtra("username");
                back.putExtra("studentId",id);
                back.putExtra("username",username);
                startActivity(back);
                finish();
            }
        });
    }
}
