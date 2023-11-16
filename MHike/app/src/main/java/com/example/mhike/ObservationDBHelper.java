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

public class ObservationDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "observations.db";
    private static final int DATABASE_VERSION = 1;

    // Observation table
    public static final String TABLE_OBSERVATION = "observation";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_OBSERVATION_TITLE = "observationTitle";
    public static final String COLUMN_HIKE_ID = "hikeId";
    public static final String COLUMN_OBSERVATION_DATE = "observationDate";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_COMMENTS = "comments";

    // SQL statement to create Observation table
    private static final String SQL_CREATE_OBSERVATION =
            "CREATE TABLE " + TABLE_OBSERVATION + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_OBSERVATION_TITLE + " TEXT," +
                    COLUMN_HIKE_ID + " INTEGER," +
                    COLUMN_OBSERVATION_DATE + " TEXT," +
                    COLUMN_PHOTO + " TEXT," +
                    COLUMN_COMMENTS + " TEXT," +
                    "FOREIGN KEY(" + COLUMN_HIKE_ID + ") REFERENCES Hike(id)" +
                    ");";

    public ObservationDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_OBSERVATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade policy for the database if needed
        // Here, you can handle version updates and schema changes
        // For simplicity, we're dropping and recreating the table in case of an upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBSERVATION);
        onCreate(db);
    }

    // Save Observation
    public void saveObservation(Observation observation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValuesFromObservation(observation);
        db.insert(TABLE_OBSERVATION, null, values);
    }

    // Get Observation
    public Observation getObservation(long observationId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_OBSERVATION, null,
                COLUMN_ID + "=?", new String[]{String.valueOf(observationId)},
                null, null, null, null);

        Observation observation = null;

        if (cursor != null) {
            cursor.moveToFirst();
            observation = getObservationFromCursor(cursor);
            cursor.close();
        }

        return observation;
    }

    // Delete Observation
    public void deleteObservation(long observationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OBSERVATION, COLUMN_ID + "= ?",
                new String[]{String.valueOf(observationId)});
        db.close();
    }

    // Get All Observations
    public List<Observation> getAllObservations(long hikeId) {
        List<Observation> observationList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_OBSERVATION_TITLE, COLUMN_HIKE_ID,
                COLUMN_OBSERVATION_DATE, COLUMN_PHOTO, COLUMN_COMMENTS};

        Cursor cursor = db.query(TABLE_OBSERVATION, null,
                COLUMN_HIKE_ID + "=?", new String[]{String.valueOf(hikeId)},
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Observation observation = getObservationFromCursor(cursor);
                observationList.add(observation);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return observationList;
    }

    public void editObservation(Observation observation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValuesFromObservation(observation);
        db.update(TABLE_OBSERVATION, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(observation.getId())});
        db.close();
    }

    private ContentValues getContentValuesFromObservation(Observation observation) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_OBSERVATION_TITLE, observation.getObservationTitle());
        values.put(COLUMN_COMMENTS, observation.getComment());
        values.put(COLUMN_OBSERVATION_DATE, observation.getObservationDate());
        values.put(COLUMN_HIKE_ID, observation.getHikeId());
        values.put(COLUMN_PHOTO, observation.getPhoto());
        return values;
    }

    @SuppressLint("Range")
    private Observation getObservationFromCursor(Cursor cursor) {
        Observation observation = new Observation();
        observation.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
        observation.setObservationTitle(cursor.getString(cursor.getColumnIndex(COLUMN_OBSERVATION_TITLE)));
        observation.setObservationDate(cursor.getString(cursor.getColumnIndex(COLUMN_OBSERVATION_DATE)));
        observation.setComment(cursor.getString(cursor.getColumnIndex(COLUMN_COMMENTS)));
        observation.setPhoto(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
        observation.setHikeId(cursor.getInt(cursor.getColumnIndex(COLUMN_HIKE_ID)));
        return observation;
    }

}
