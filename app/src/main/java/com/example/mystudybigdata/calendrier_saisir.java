package com.example.mystudybigdata;
import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class calendrier_saisir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier_saisir);

        Button b = findViewById(R.id.button2);  // Button to add the event

        DatabaseHelper dbHelper = new DatabaseHelper(calendrier_saisir.this);

        b.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {
                String description = DonnerDescription();  // Get the description based on the selected radio button

                // Get the data passed from the previous activity
                Intent i = getIntent();
                int id=i.getIntExtra("studentId",-1);
                String date = i.getStringExtra("date");  // Date selected in the calendar activity


                // Add the homework event to the database
                dbHelper.addHomework(description, date, id);
                Toast.makeText(getApplicationContext(),"Added " + description,Toast.LENGTH_SHORT).show();
                // Set result to indicate success and return to the previous activity (calendrier)
                setResult(RESULT_OK);
                setNotification(parseDate(date), description);
                finish();

                // Close the current activity and return to the previous one
            }
        });

        // Button to go back to the previous activity
        ImageButton b2 = findViewById(R.id.imageButton10);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRetour = new Intent(calendrier_saisir.this, Activity2.class);
                startActivity(intentRetour);
                finish();  // Close this activity
            }
        });
    }

    // Method to get the description based on the selected radio button
    public String DonnerDescription() {
        RadioGroup radioGroup = findViewById(R.id.radioGroup); // Use your actual RadioGroup ID
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.radioButton4) {
            return "DS";
        }
        if (selectedId == R.id.radioButton5) {
            return "EXAMEN";
        }
        if (selectedId == R.id.radioButton6) {
            return "projet";
        }
        if (selectedId == R.id.radioButton7) {
            return "presentation";
        }
        if (selectedId == R.id.radioButton8) {
            return "evennement";
        } else {
            return "";  // Default if no radio button is selected
        }
    }

    public void setNotification(Calendar dueDate, String description) {
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("description", description);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // 3) Get the AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            Log.w("AlarmSetup", "AlarmManager not available");
            return;
        }

        dueDate.set(Calendar.HOUR_OF_DAY, 8);
        dueDate.set(Calendar.MINUTE, 0);
        dueDate.set(Calendar.SECOND, 0);
        long triggerAtMillis = dueDate.getTimeInMillis();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Log.w("AlarmSetup", "Cannot schedule exact alarms—permission missing");
                return;
            }
        }

        // 6) Schedule the alarm, catching SecurityException just in case
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent
                );
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent
                );
            } else {
                alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent
                );
            }
        } catch (SecurityException e) {
            Log.e("AlarmSetup", "Failed to schedule exact alarm", e);
            // Handle gracefully: maybe fall back or inform the user
        }
    }



    // Method to parse the date from string (DD/MM/YYYY format) to Calendar object
    public Calendar parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  // Specify the date format
        Calendar calendar = Calendar.getInstance();

        try {
            Date date = dateFormat.parse(dateStr);  // Parse the date string
            calendar.setTime(date);  // Set the calendar's time to the parsed date
        } catch (Exception e) {
            e.printStackTrace();  // Handle any parsing exceptions
        }

        return calendar;
    }
}
