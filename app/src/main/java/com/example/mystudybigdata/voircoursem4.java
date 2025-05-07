package com.example.mystudybigdata;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class voircoursem4 extends AppCompatActivity {
    private static final int REQUEST_PERMISSION = 100;

    // Button and PDF arrays
    private Button[] buttons;
    private final int[] pdfResIds = {
            R.raw.ailkol,
            R.raw.mobileee,
            R.raw.bigdata,
            R.raw.weeb,
            R.raw.r,
            R.raw.datamining

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voircoursem4);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttons = new Button[] {
                findViewById(R.id.button12),
                findViewById(R.id.button7),
                findViewById(R.id.button9),
                findViewById(R.id.button8),
                findViewById(R.id.button13),
                findViewById(R.id.button16)

        };

        // Loop through buttons and assign click listeners
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].setOnClickListener(view -> {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_PERMISSION);
                    } else {
                        savePdfToDownloadsLegacy(index);
                    }
                } else {
                    savePdfToDownloadsModern(index);
                }
            });
        }
        ImageButton retour = findViewById(R.id.home5);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_retour = new Intent(voircoursem4.this,Activity2.class);
                int id=getIntent().getIntExtra("id",1);
                intent_retour.putExtra("id",id);
                startActivity(intent_retour);
                finish();
            }
        });
    }

    private void savePdfToDownloadsModern(int index) {
        try {
            InputStream in = getResources().openRawResource(pdfResIds[index]);

            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, "cours_" + (index + 1) + ".pdf");
            values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
            values.put(MediaStore.MediaColumns.IS_PENDING, 1);

            ContentResolver resolver = getContentResolver();
            Uri collection = MediaStore.Files.getContentUri("external");
            Uri fileUri = resolver.insert(collection, values);

            if (fileUri != null) {
                OutputStream out = resolver.openOutputStream(fileUri);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                in.close();
                out.close();

                values.clear();
                values.put(MediaStore.MediaColumns.IS_PENDING, 0);
                resolver.update(fileUri, values, null, null);

                Toast.makeText(this, "PDF saved to Downloads folder", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to create file", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Download failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void savePdfToDownloadsLegacy(int index) {
        try {
            InputStream in = getResources().openRawResource(pdfResIds[index]);
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File outFile = new File(downloadsDir, "cours_" + (index + 1) + ".pdf");

            OutputStream out = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();

            Toast.makeText(this, "PDF saved to Downloads folder", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Download failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                savePdfToDownloadsLegacy(0); // default to first file if no index stored
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
