package com.example.contactdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ContactDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ContactsDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CONTACTS = "contacts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_DOB = "dob";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_CONTACTS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_DOB + " TEXT);";

    public ContactDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    // Function to save a new contact
    public long saveContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        long contactId = -1;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, contact.getName());
            values.put(COLUMN_EMAIL, contact.getEmail());
            values.put(COLUMN_DOB, contact.getDob());

            contactId = db.insert(TABLE_CONTACTS, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return contactId;
    }

    // Function to get all contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Log.v("DB_HELPER", "GETTING ALL CONTACTS");

        try {
            Cursor cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        Contact contact = new Contact();
                        contact.set_id(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                        contact.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                        contact.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                        contact.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_DOB)));

                        contactList.add(contact);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.v("DB_HELPER", e.toString());
        } finally {
            db.close();
        }

        return contactList;
    }

    // Other database operations (update, delete, etc.)
}
