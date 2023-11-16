package com.example.contactdb;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private ContactDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new ContactDBHelper(this);

        // Retrieve the list of contacts from the database
        List<Contact> contactList = dbHelper.getAllContacts();

        // Create the adapter and set it to the RecyclerView
        adapter = new ContactAdapter(this, contactList);
        recyclerView.setAdapter(adapter);
    }
}
