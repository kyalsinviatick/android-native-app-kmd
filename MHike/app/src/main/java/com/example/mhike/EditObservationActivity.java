package com.example.mhike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditObservationActivity extends AppCompatActivity {

    private EditText editTextObservationTitle, editTextComments;

    private Button buttonSubmit;

    private ObservationDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_observation);

        editTextObservationTitle = findViewById(R.id.editTextObservationTitle);
        editTextComments = findViewById(R.id.editTextComments);

        Intent intent = getIntent();

        Observation observation = (Observation) intent.getSerializableExtra("observationObject");

        if (intent != null) {
            editTextObservationTitle.setText(observation.getObservationTitle());
            editTextComments.setText(observation.getComment());
        }

        buttonSubmit = findViewById(R.id.buttonSubmit);

        dbHelper = new ObservationDBHelper(this);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateObservation(observation);
                finish();
                startActivity(getIntent());
            }
        });
    }

    private void updateObservation(Observation observation) {
        // Retrieve values from input fields
        String title = editTextObservationTitle.getText().toString();
        String comment = editTextComments.getText().toString();

        if (title.isEmpty()) {
            // Show a Toast message if any of the fields are empty
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {

            // Display a toast message as an example
            String message = "Title: " + title +
                    "\nComment: " + comment;

            Log.d("ADD_HIKE_ACTIVITY", message);

            observation.setObservationTitle(title);
            observation.setComment(comment);

            dbHelper.editObservation(observation);

            Toast.makeText(this, "Edited successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}