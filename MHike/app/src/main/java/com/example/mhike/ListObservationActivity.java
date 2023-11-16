package com.example.mhike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ListObservationActivity extends AppCompatActivity {

    private List<Observation> observationList;
    private ObservationAdapter observationAdapter;
    private ObservationDBHelper dbHelper;

    private long hikeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_observation);

        hikeId = (long) getIntent().getIntExtra("hikeId", -1);

        dbHelper = new ObservationDBHelper(this);
        observationList = dbHelper.getAllObservations(hikeId);

        Log.d("LIST OBSERVATION ACTIVITY", observationList.toString());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TextView emptyTextView = findViewById(R.id.emptyTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        observationAdapter = new ObservationAdapter(this, observationList);
        recyclerView.setAdapter(observationAdapter);

        if (observationList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }
    }
}