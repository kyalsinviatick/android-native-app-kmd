package com.example.contactdb;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private EditText nameEditText, emailEditText, dobEditText;
    private Button addButton, viewButton;
    private Button datepickerButton;
    private Calendar selectedDate;
    private ContactDBHelper dbHelper;

    private String selectedDateValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        dobEditText = findViewById(R.id.dobEditText);
        addButton = findViewById(R.id.addButton);
        viewButton = findViewById(R.id.viewButton);

        dobEditText.setVisibility(View.GONE);

        datepickerButton = findViewById(R.id.datepickerButton);

        dbHelper = new ContactDBHelper(this);

        datepickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement the logic to save a contact
                saveContact();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the contact list activity
                Intent intent = new Intent(MainActivity.this, ContactListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);

                        // Format the selected date as desired (e.g., "yyyy-MM-dd")
                        String formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);

                        // Display the selected date in an EditText or TextView
                        datepickerButton.setText(formattedDate);
                        dobEditText.setText(formattedDate);
                    }
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }

    private void saveContact() {
        // Retrieve data from EditText fields and save the contact using the dbHelper
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String dob = dobEditText.getText().toString();

        if (name.isEmpty() || email.isEmpty() || dob.isEmpty()) {
            // Show a Toast message if any of the fields are empty
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Create a new Contact object with the data
            Contact contact = new Contact(name, email, dob); // 0 for imageId

            long contactId = dbHelper.saveContact(contact);

            if (contactId > 0) {
                // Contact saved successfully
                Toast.makeText(this, "Contact added successfully!", Toast.LENGTH_SHORT).show();

                // Clear the EditText fields
                nameEditText.setText("");
                emailEditText.setText("");
                dobEditText.setText("");
                datepickerButton.setText("Click to select Birthday");
            } else {
                // Failed to save contact
                Toast.makeText(this, "Failed to add contact", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

