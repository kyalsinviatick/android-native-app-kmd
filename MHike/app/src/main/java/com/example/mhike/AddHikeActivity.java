package com.example.mhike;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

public class AddHikeActivity extends AppCompatActivity {

    private EditText editTextName, editTextLocation, editViewLength, editViewDifficulty,
            editTextDescription, editTextElevation, editTextTimeToComplete, dateOfHike;

    private Button datePickerButton;

    private Switch switchParkingToggle;

    private Calendar selectedDate;

    private HikeDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hike);

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
                saveHike();
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

    private void saveHike() {
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

            Hike hike = new Hike(name, location, date, isParkingAvailable, Double.parseDouble(length), difficulty, description, Integer.parseInt(elevation), timeToComplete);

            dbHelper.saveHike(hike);

            editTextName.setText("");
            editTextLocation.setText("");
            editViewLength.setText("");
            editViewDifficulty.setText("");
            editTextDescription.setText("");
            editTextElevation.setText("");
            editTextTimeToComplete.setText("");
            dateOfHike.setText("Select Date");
            datePickerButton.setText("");
            switchParkingToggle.setChecked(false);


            Toast.makeText(this, "Hike added successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}