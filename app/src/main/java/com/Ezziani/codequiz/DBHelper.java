package com.Ezziani.codequiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "codequiz.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_HISTORY = "quiz_history";
    private static final String COL_ID = "id";
    private static final String COL_USER = "user_email";
    private static final String COL_LANGUAGE = "language";
    private static final String COL_DIFFICULTY = "difficulty";
    private static final String COL_SCORE = "score";
    private static final String COL_TOTAL = "total";
    private static final String COL_DATE = "date";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_HISTORY + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER + " TEXT, " +
                COL_LANGUAGE + " TEXT, " +
                COL_DIFFICULTY + " TEXT, " +
                COL_SCORE + " INTEGER, " +
                COL_TOTAL + " INTEGER, " +
                COL_DATE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    public void insertHistory(String userEmail, String language,
                              String difficulty, int score, int total, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER, userEmail);
        values.put(COL_LANGUAGE, language);
        values.put(COL_DIFFICULTY, difficulty);
        values.put(COL_SCORE, score);
        values.put(COL_TOTAL, total);
        values.put(COL_DATE, date);
        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }

    public ArrayList<String> getHistory(String userEmail) {
        ArrayList<String> history = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HISTORY, null,
                COL_USER + "=?", new String[]{userEmail},
                null, null, COL_ID + " DESC");

        while (cursor.moveToNext()) {
            String language = cursor.getString(cursor.getColumnIndexOrThrow(COL_LANGUAGE));
            String difficulty = cursor.getString(cursor.getColumnIndexOrThrow(COL_DIFFICULTY));
            int score = cursor.getInt(cursor.getColumnIndexOrThrow(COL_SCORE));
            int total = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TOTAL));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
            history.add(language + " | " + difficulty + " | " + score + "/" + total + " | " + date);
        }
        cursor.close();
        db.close();
        return history;
    }

    public void clearHistory(String userEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, COL_USER + "=?", new String[]{userEmail});
        db.close();
    }
}