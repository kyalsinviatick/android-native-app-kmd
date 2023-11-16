package com.example.mhike;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

public class EditHikeActivity extends AppCompatActivity {

    private EditText editTextName, editTextLocation, editViewLength, editViewDifficulty,
            editTextDescription, editTextElevation, editTextTimeToComplete, dateOfHike;

    private Button datePickerButton;

    private Switch switchParkingToggle;

    private Calendar selectedDate;

    private HikeDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);

        // Initialize UI elements
        editTextName = findViewById(R.id.editTextName);
        editTextLocation = findViewById(R.id.editTextLocation);
        editViewLength = findViewById(R.id.editViewLength);
        editViewDifficulty = findViewById(R.id.editViewDifficulty);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextElevation = findViewById(R.id.editTextElevation);
        editTextTimeToComplete = findViewById(R.id.editTextTimeToComplete);
        dateOfHike = findViewById(R.id.dateOfHike);
        switchParkingToggle = findViewById(R.id.switchParkingToggle);
        datePickerButton = findViewById(R.id.datePickerButton);
        dateOfHike.setVisibility(View.GONE);

        Intent intent = getIntent();

        Hike receivedHike = (Hike) intent.getSerializableExtra("hikeObject");

        if (intent != null) {
            editTextName.setText(receivedHike.getName());
            editTextLocation.setText(receivedHike.getLocation());
            editViewLength.setText(String.valueOf(receivedHike.getLength()));
            editViewDifficulty.setText(receivedHike.getDifficulty());
            editTextDescription.setText(receivedHike.getDescription());
            editTextElevation.setText(String.valueOf(receivedHike.getElevation()));
            editTextTimeToComplete.setText(receivedHike.getTimeToComplete());
            dateOfHike.setText(receivedHike.getDate());
            switchParkingToggle.setChecked(receivedHike.isParkingStatus());
            datePickerButton.setText(receivedHike.getDate());
        }

        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        dbHelper = new HikeDBHelper(this);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        // Set a click listener for the submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateHike(receivedHike);
                finish();
                startActivity(getIntent());
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
                        datePickerButton.setText(formattedDate);
                        dateOfHike.setText(formattedDate);
                    }
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }

    private void updateHike(Hike hike) {
        // Retrieve values from input fields
        String name = editTextName.getText().toString();
        String location = editTextLocation.getText().toString();
        String length = editViewLength.getText().toString();
        String difficulty = editViewDifficulty.getText().toString();
        String description = editTextDescription.getText().toString();
        String elevation = editTextElevation.getText().toString();
        String timeToComplete = editTextTimeToComplete.getText().toString();
        String date = dateOfHike.getText().toString();
        boolean isParkingAvailable = switchParkingToggle.isChecked();

        if (name.isEmpty() || location.isEmpty() || date.isEmpty() || length.isEmpty() || description.isEmpty() || elevation.isEmpty() || timeToComplete.isEmpty()) {
            // Show a Toast message if any of the fields are empty
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {

            // Display a toast message as an example
            String message = "Name: " + name +
                    "\nLocation: " + location +
                    "\nLength: " + length +
                    "\nDifficulty: " + difficulty +
                    "\nDescription: " + description +
                    "\nElevation: " + elevation +
                    "\nTime To Complete: " + timeToComplete +
                    "\nDate: " + date +
                    "\nParking Available: " + isParkingAvailable;

            Log.d("ADD_HIKE_ACTIVITY", message);

            hike.setName(name);
            hike.setLocation(location);
            hike.setLength(Double.parseDouble(length));
            hike.setDifficulty(difficulty);
            hike.setDescription(description);
            hike.setElevation(Integer.parseInt(elevation));
            hike.setTimeToComplete(timeToComplete);
            hike.setDate(date);
            hike.setParkingStatus(isParkingAvailable);

            dbHelper.editHike(hike);

            Toast.makeText(this, "Edited successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}