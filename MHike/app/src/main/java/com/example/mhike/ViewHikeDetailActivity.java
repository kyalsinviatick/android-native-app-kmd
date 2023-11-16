package com.example.mhike;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewHikeDetailActivity extends AppCompatActivity {

    private Hike hike;

    private HikeDBHelper dbHelper;


    TextView textViewName;
    TextView textViewLocation;
    TextView textViewDate;
    TextView textViewParkingStatus;
    TextView textViewLength;
    TextView textViewDifficulty;
    TextView textViewDescription;
    TextView textViewElevation;
    TextView textViewTimeToComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hike_detail);

        textViewName = findViewById(R.id.textViewDetailName);
        textViewLocation = findViewById(R.id.textViewDetailLocation);
        textViewDate = findViewById(R.id.textViewDetailDate);
        textViewParkingStatus = findViewById(R.id.textViewDetailParkingStatus);
        textViewLength = findViewById(R.id.textViewDetailLength);
        textViewDifficulty = findViewById(R.id.textViewDetailDifficulty);
        textViewDescription = findViewById(R.id.textViewDetailDescription);
        textViewElevation = findViewById(R.id.textViewDetailElevation);
        textViewTimeToComplete = findViewById(R.id.textViewDetailTimeToComplete);

        dbHelper = new HikeDBHelper(this);

        int hikeId = getIntent().getIntExtra("hikeId", -1);
        Log.d("VIEW HIKE DETAIL ACTIVITY", Integer.toString(hikeId));

        if (hikeId > -1) {
            hike = dbHelper.getHike(hikeId);

            textViewName.setText("Name: " + hike.getName());
            textViewLocation.setText("Location: " + hike.getLocation());
            textViewDate.setText("Date: " + hike.getDate());
            textViewParkingStatus.setText("Parking Status: " + (hike.isParkingStatus() ? "Available" : "Not Available"));
            textViewLength.setText("Length: " + hike.getLength() + " miles");
            textViewDifficulty.setText("Difficulty: " + hike.getDifficulty());
            textViewDescription.setText("Description: " + hike.getDescription());
            textViewElevation.setText("Elevation: " + hike.getElevation() + " feet");
            textViewTimeToComplete.setText("Time to Complete: " + hike.getTimeToComplete());
        }

        Button buttonAddObservation = findViewById(R.id.buttonAddObservation);
        Button buttonViewObservation = findViewById(R.id.buttonViewObservation);

        buttonAddObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to AddObservationActivity
                Intent intent = new Intent(ViewHikeDetailActivity.this, AddObservationActivity.class);
                intent.putExtra("hikeId", hikeId);
                startActivity(intent);
            }
        });

        buttonViewObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to ViewObservationActivity
                Intent intent = new Intent(ViewHikeDetailActivity.this, ListObservationActivity.class);
                intent.putExtra("hikeId", hikeId);
                startActivity(intent);
            }
        });
    }
}