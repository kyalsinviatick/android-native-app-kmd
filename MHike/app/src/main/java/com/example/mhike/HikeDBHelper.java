package com.example.mhike;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HikeDBHelper extends SQLiteOpenHelper {

    // Database Information
    private static final String DATABASE_NAME = "hike_database";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    private static final String TABLE_HIKES = "hikes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LENGTH = "length";
    private static final String COLUMN_DIFFICULTY = "difficulty";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_ELEVATION = "elevation";
    private static final String COLUMN_TIME_TO_COMPLETE = "time_to_complete";
    private static final String COLUMN_PARKING_STATUS = "parking_status";

    // Database creation SQL statement
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_HIKES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_LOCATION + " TEXT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_LENGTH + " TEXT, " +
                    COLUMN_DIFFICULTY + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_ELEVATION + " TEXT, " +
                    COLUMN_TIME_TO_COMPLETE + " TEXT, " +
                    COLUMN_PARKING_STATUS + " INTEGER DEFAULT 0);";

    public HikeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIKES);
        onCreate(db);
    }

    // Save a new hike
    public void saveHike(Hike hike) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValuesFromHike(hike);
        db.insert(TABLE_HIKES, null, values);
        db.close();
    }

    // Edit an existing hike
    public void editHike(Hike hike) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValuesFromHike(hike);
        db.update(TABLE_HIKES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(hike.getId())});
        db.close();
    }

    // Delete a hike
    public void deleteHike(int hikeId) {
        Log.d("DB HELPER", Integer.toString(hikeId));
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HIKES, COLUMN_ID + " = ?",
                new String[]{String.valueOf(hikeId)});
        db.close();
    }

    // Get a single hike by ID
    public Hike getHike(int hikeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HIKES, null,
                COLUMN_ID + "=?", new String[]{String.valueOf(hikeId)},
                null, null, null, null);

        Hike hike = null;

        if (cursor != null) {
            cursor.moveToFirst();
            hike = getHikeFromCursor(cursor);
            cursor.close();
        }

        return hike;
    }

    // Get all hikes
    public List<Hike> getAllHikes() {
        List<Hike> hikeList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_HIKES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Hike hike = getHikeFromCursor(cursor);
                hikeList.add(hike);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return hikeList;
    }

    // Helper method to convert Hike object to ContentValues
    private ContentValues getContentValuesFromHike(Hike hike) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, hike.getName());
        values.put(COLUMN_LOCATION, hike.getLocation());
        values.put(COLUMN_DATE, hike.getDate());
        values.put(COLUMN_LENGTH, hike.getLength());
        values.put(COLUMN_DIFFICULTY, hike.getDifficulty());
        values.put(COLUMN_DESCRIPTION, hike.getDescription());
        values.put(COLUMN_ELEVATION, hike.getElevation());
        values.put(COLUMN_TIME_TO_COMPLETE, hike.getTimeToComplete());
        values.put(COLUMN_PARKING_STATUS, hike.isParkingStatus() ? 1 : 0);
        return values;
    }

    // Helper method to convert Cursor to Hike object
    @SuppressLint("Range")
    private Hike getHikeFromCursor(Cursor cursor) {
        Hike hike = new Hike();
        hike.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        hike.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        hike.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
        hike.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
        hike.setLength(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_LENGTH))));
        hike.setDifficulty(cursor.getString(cursor.getColumnIndex(COLUMN_DIFFICULTY)));
        hike.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
        hike.setElevation(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ELEVATION))));
        hike.setTimeToComplete(cursor.getString(cursor.getColumnIndex(COLUMN_TIME_TO_COMPLETE)));
        hike.setParkingStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_PARKING_STATUS)) == 1);
        return hike;
    }
}

