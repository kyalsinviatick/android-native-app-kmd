package com.example.mhike;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListHikeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HikeAdapter hikeAdapter;
    private HikeDBHelper dbHelper;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hike);

        dbHelper = new HikeDBHelper(this);

        recyclerView = findViewById(R.id.recyclerViewHikes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        emptyTextView = findViewById(R.id.emptyTextView);

        // Retrieve all hikes initially
        List<Hike> allHikes = dbHelper.getAllHikes();
        hikeAdapter = new HikeAdapter(this, allHikes);
        recyclerView.setAdapter(hikeAdapter);

        setupSearchView();
        updateEmptyViewVisibility();
    }

    private void setupSearchView() {
        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Update the filter when the text changes
                hikeAdapter.getFilter().filter(newText);
                updateEmptyViewVisibility();
                return false;
            }
        });
    }

    private void updateEmptyViewVisibility() {
        emptyTextView.setVisibility(hikeAdapter.getItemCount() == 0 ? TextView.VISIBLE : TextView.GONE);
    }

}

// ListHikeActivity.java

//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ListHikeActivity extends AppCompatActivity {
//
//    private List<Hike> hikeList;
//    private HikeAdapter hikeAdapter;
//
//    private HikeDBHelper dbHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_hike);
//
//        dbHelper = new HikeDBHelper(this);
//
//        // Sample data
//        hikeList = dbHelper.getAllHikes();
//
//        Log.d("LIST_HIKE_ACTIVITY", hikeList.toString());
//
//        // RecyclerView setup
//        RecyclerView recyclerView = findViewById(R.id.recyclerViewHikes);
//        TextView emptyTextView = findViewById(R.id.emptyTextView);
//
//        // Create a DividerItemDecoration and set the drawable resource
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
//        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
//
//        // Set the layout manager and add the divider decoration
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(dividerItemDecoration);
//
//        hikeAdapter = new HikeAdapter(this, hikeList);
//        recyclerView.setAdapter(hikeAdapter);
//
//        if (hikeList.isEmpty()) {
//            recyclerView.setVisibility(View.GONE);
//            emptyTextView.setVisibility(View.VISIBLE);
//        } else {
//            recyclerView.setVisibility(View.VISIBLE);
//            emptyTextView.setVisibility(View.GONE);
//        }
//    }
//
//    private List<Hike> generateSampleData() {
//        List<Hike> sampleHikes = new ArrayList<>();
//        // Populate the list with sample data
//        sampleHikes.add(new Hike("Hike 1", "Location 1", "2023-11-15", true, 5.0, "Moderate", "Sample description", 1000, "2 hours"));
//        sampleHikes.add(new Hike("Hike 2", "Location 2", "2023-11-20", false, 8.0, "Challenging", "Another description", 1500, "3 hours"));
//        // Add more sample hikes as needed
//        return sampleHikes;
//    }
//}
