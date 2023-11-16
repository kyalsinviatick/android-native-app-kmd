package com.example.mhike;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewObservationDetailActivity extends AppCompatActivity {

    private Observation observation;
    private ObservationDBHelper dbHelper;

    TextView textViewDetailTitle, textViewDetailDate, textViewDetailComments;

    ImageView imageViewDetailPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_observation_detail);

        textViewDetailTitle = findViewById(R.id.textViewDetailTitle);
        textViewDetailDate = findViewById(R.id.textViewDetailDate);
        textViewDetailComments = findViewById(R.id.textViewDetailComments);

        dbHelper = new ObservationDBHelper(this);

        long observationId = (long) getIntent().getIntExtra("observationId", -1);

        Log.d("VIEW OBSERVATION DETAIL", Long.toString(observationId));

        if (observationId > -1) {

            observation = dbHelper.getObservation(observationId);

            Log.d("VIEW OBSERVATION DETAIL", observation.toString());

            textViewDetailTitle.setText("Title: " + observation.getObservationTitle());
            textViewDetailDate.setText("Date: " + observation.getObservationDate());
            textViewDetailComments.setText("Comment: " + observation.getComment());

        }

    }
}