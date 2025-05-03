package com.example.mystudybigdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME    = "StudyApp.db";
    private static final int    DATABASE_VERSION = 5;

    // Table: Students
    public static final String TABLE_STUDENTS   = "students";
    public static final String COL_STUDENT_ID   = "id";
    public static final String COL_NAME         = "name";
    public static final String COL_EMAIL        = "email";
    public static final String COL_PASSWORD     = "password";
    public static final String COL_BIRTHDAY     = "birthday";


    // Table: Homework
    public static final String TABLE_HOMEWORK      = "homework";
    public static final String COL_HW_ID           = "id";
    public static final String COL_HW_DESCRIPTION  = "description";
    public static final String COL_HW_DATE         = "due_date";
    public static final String COL_STUDENT_HW_ID   = "student_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStudents =
                "CREATE TABLE " + TABLE_STUDENTS + " (" +
                        COL_STUDENT_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NAME        + " TEXT NOT NULL, " +
                        COL_EMAIL       + " TEXT UNIQUE NOT NULL, " +
                        COL_PASSWORD    + " TEXT NOT NULL, " +

                        COL_BIRTHDAY    + " TEXT" +
                        ");";

        String createHomework =
                "CREATE TABLE " + TABLE_HOMEWORK + " (" +
                        COL_HW_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_HW_DESCRIPTION+ " TEXT, " +
                        COL_HW_DATE       + " TEXT NOT NULL, " +
                        COL_STUDENT_HW_ID + " INTEGER NOT NULL, " +
                        "FOREIGN KEY("   + COL_STUDENT_HW_ID +
                        ") REFERENCES " + TABLE_STUDENTS +
                        "(" + COL_STUDENT_ID + ")" +
                        ");";

        db.execSQL(createStudents);
        db.execSQL(createHomework);
    }

    // Called if database version changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOMEWORK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    // Sign up user
    public boolean registerStudent(String name, String email,
                                   String password, String birthday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_NAME,name);
        values.put(COL_EMAIL,email);
        values.put(COL_PASSWORD,password);
        values.put(COL_BIRTHDAY,birthday);
               // correct key

        long result = db.insert(TABLE_STUDENTS, null, values);
        db.close();
        if(result==-1){
            Log.d("CalendarDebug",
                    "Error inserting homework. Description: ");
        }
        return result != -1;
    }

    // Check login
    public boolean loginStudent(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT 1 FROM " + TABLE_STUDENTS +
                        " WHERE " + COL_NAME + " = ? AND " + COL_PASSWORD + " = ?",
                new String[]{ name, password }
        );

        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    // Add homework for a student
    public boolean addHomework(String description, String dueDate, int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_HW_DESCRIPTION, description);
        values.put(COL_HW_DATE,        dueDate);    // e.g. "DD/MM/YYYY"
        values.put(COL_STUDENT_HW_ID,  studentId);  // correct param name

        long result = db.insert(TABLE_HOMEWORK, null, values);
        if (result == -1) {
            Log.d("CalendarDebug",
                    "Error inserting homework. Description: " + description +
                            ", Due Date: " + dueDate +
                            ", Student ID: " + studentId);
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    // Fetch all homework entries
    public Cursor fetchAllHomework() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT " + COL_HW_DATE + ", " + COL_HW_DESCRIPTION +
                        " FROM " + TABLE_HOMEWORK,
                null
        );
    }
    public int getStudentId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = { "id" };
        String   sel  = "name = ? AND password = ?";
        String[] args = { username, password };

        Cursor c = db.query("students", cols, sel, args, null, null, null);
        if (c != null && c.moveToFirst()) {
            int id = c.getInt(c.getColumnIndexOrThrow("id"));
            c.close();
            return id;
        }
        if (c != null) c.close();
        return -1;
    }

}
