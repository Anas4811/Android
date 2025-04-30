package com.example.mystudybigdata;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class calendrier extends AppCompatActivity {

    private CalendarView calendarView;
    private Map<String, String> eventMap;
    private List<EventDay> eventDays = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        Intent intent = getIntent();
        int studentId = intent.getIntExtra("studentId", -1);
        if (studentId < 0) {
            Toast.makeText(this, "Invalid student ID!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        calendarView = findViewById(R.id.calendarView);

        // 1) Fetch and mark all events
        eventMap = getEventsFromDB(studentId);
        markEvents(studentId, eventMap);

        // 2) Day‑click listener
        calendarView.setOnDayClickListener(eventDay -> {
            Calendar clicked = eventDay.getCalendar();
            String selectedDate = clicked.get(Calendar.DAY_OF_MONTH)
                    + "/" + (clicked.get(Calendar.MONTH) + 1)
                    + "/" + clicked.get(Calendar.YEAR);
            Toast.makeText(this, "Selected: " + selectedDate, Toast.LENGTH_SHORT).show();

            if (eventMap.containsKey(selectedDate)) {
                showEventInDialog(eventMap.get(selectedDate));
            } else {
                Intent i = new Intent(calendrier.this, calendrier_saisir.class);
                i.putExtra("studentId", studentId);
                i.putExtra("date", selectedDate);
                startActivityForResult(i, 2);
            }
        });

        // 3) Home button
        ((ImageButton) findViewById(R.id.HomeimageButton)).setOnClickListener(v -> {
            startActivity(new Intent(calendrier.this, Activity2.class));
            finish();
        });
    }

    private void markEvents(int studentId, Map<String, String> eventMap) {
        eventDays.clear();
        Log.d("CalendarDebug", "Homework events: " + eventMap.size());

        // — Birthday for ±2 years —
        String birthday = getStudentBirthday(studentId);
        if (birthday != null) {
            Integer birthDay = null, birthMonth = null;
            // Detect format
            if (birthday.contains("-")) {
                // YYYY-MM-DD
                String[] parts = birthday.split("-");
                if (parts.length == 3) {
                    try {
                        birthDay   = Integer.parseInt(parts[2]);
                        birthMonth = Integer.parseInt(parts[1]) - 1;
                    } catch (NumberFormatException e) {
                        Log.e("CalendarDebug", "Bad numeric in birthday: " + birthday, e);
                    }
                } else {
                    Log.e("CalendarDebug", "Unexpected '-' split length: " + birthday);
                }
            } else if (birthday.contains("/")) {
                // MM/DD/YYYY
                String[] parts = birthday.split("/");
                if (parts.length == 3) {
                    try {
                        birthDay   = Integer.parseInt(parts[1]);
                        birthMonth = Integer.parseInt(parts[0]) - 1;
                    } catch (NumberFormatException e) {
                        Log.e("CalendarDebug", "Bad numeric in birthday: " + birthday, e);
                    }
                } else {
                    Log.e("CalendarDebug", "Unexpected '/' split length: " + birthday);
                }
            }

            if (birthDay != null && birthMonth != null) {
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int spanYears   = 2;
                for (int year = currentYear - spanYears; year <= currentYear + spanYears; year++) {
                    Calendar c = Calendar.getInstance();
                    c.set(year, birthMonth, birthDay, 0, 0, 0);
                    Drawable cake = ContextCompat.getDrawable(this, R.drawable.birthday);
                    eventDays.add(new EventDay(c, cake));
                    Log.d("CalendarDebug", "Marked birthday for: " + c.getTime());
                }
            }
        }

        // — Homework events —
        for (Map.Entry<String, String> entry : eventMap.entrySet()) {
            String dateStr     = entry.getKey();
            String description = entry.getValue();

            String[] parts = dateStr.split("/");
            if (parts.length == 3) {
                try {
                    int day   = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]) - 1;
                    int year  = Integer.parseInt(parts[2]);
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, month, day);
                    Drawable dot = ContextCompat.getDrawable(this, R.drawable.dot);
                    eventDays.add(new EventDay(cal, dot));
                } catch (NumberFormatException ex) {
                    Log.e("CalendarDebug", "Bad date format: " + dateStr, ex);
                }
            } else {
                Log.e("CalendarDebug", "Unexpected dateParts length: " + dateStr);
            }
        }

        calendarView.setEvents(eventDays);
        Log.d("CalendarDebug", "Total markers: " + eventDays.size());
    }

    private void showEventInDialog(String eventDescription) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Event Details")
                .setMessage(eventDescription)
                .setPositiveButton("OK", (d, i) -> d.dismiss())
                .show();
    }

    private Map<String, String> getEventsFromDB(int studentId) {
        Map<String, String> map = new HashMap<>();
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        String sql  = "SELECT due_date, description FROM homework WHERE student_id = ?";
        String[] args = { String.valueOf(studentId) };
        Cursor c = db.rawQuery(sql, args);
        if (c.moveToFirst()) {
            do {
                map.put(c.getString(0), c.getString(1));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return map;
    }

    @SuppressLint("Range")
    private String getStudentBirthday(int studentId) {
        String bday = null;
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        String sql  = "SELECT birthday FROM students WHERE id = ?";
        String[] args = { String.valueOf(studentId) };
        Cursor c = db.rawQuery(sql, args);
        if (c.moveToFirst()) {
            bday = c.getString(c.getColumnIndex("birthday"));
        }
        c.close();
        db.close();
        return bday;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            int studentId = getIntent().getIntExtra("studentId", -1);
            eventMap = getEventsFromDB(studentId);
            markEvents(studentId, eventMap);
        }
    }
}
