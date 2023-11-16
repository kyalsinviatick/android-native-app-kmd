package com.example.mhike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddObservationActivity extends AppCompatActivity {

    private EditText editTextObservationTitle, editTextComments;
    private Button buttonSubmit, buttonCapturePhoto;

    private long observationId;

    private ObservationDBHelper dbHelper;

    private ImageView imageViewCapturedPhoto;
    private Observation observation;
    private static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);

        observation = new Observation();

        editTextObservationTitle = findViewById(R.id.editTextObservationTitle);
        editTextComments = findViewById(R.id.editTextComments);

        buttonSubmit = findViewById(R.id.buttonSubmit);

        dbHelper = new ObservationDBHelper(this);

        observationId = (long) getIntent().getIntExtra("hikeId", -1);

        // Initialize views
        imageViewCapturedPhoto = findViewById(R.id.imageViewCapturedPhoto);

        buttonCapturePhoto = findViewById(R.id.buttonCapturePhoto);
        buttonCapturePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveObservation(observationId);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            Log.d("ADD OBSERVATION ACTIVITY", "HEREE<<<<");

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        } catch (Exception e) {
            Log.d("ADD OBSERVATION ACTIVITY", "ERROR OPENING CAMERA");
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            displayCapturedPhoto(imageBitmap);
        }
    }

    private void displayCapturedPhoto(Bitmap photoBitmap) {

        // Save the photo as a file
        File photoFile = saveBitmapToFile(photoBitmap);

        // Save the photo path in the Observation object
        observation.setPhoto(photoFile.getAbsolutePath());

        // Show the captured photo in the ImageView
        imageViewCapturedPhoto.setVisibility(View.VISIBLE);
        imageViewCapturedPhoto.setImageBitmap(photoBitmap);
    }

    private File saveBitmapToFile(Bitmap bitmap) {
        // Create a file to save the image
        File photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "IMG_" + System.currentTimeMillis() + ".jpg");

        try (FileOutputStream out = new FileOutputStream(photoFile)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return photoFile;
    }

    private void saveObservation(long hikeId) {
        String observationTitle = editTextObservationTitle.getText().toString();
        String observationComment = editTextComments.getText().toString();

        if (observationTitle.isEmpty()) {
            Toast.makeText(this, "Please fill in the observationTitle", Toast.LENGTH_SHORT).show();
        } else {

            LocalDateTime currentDateTime = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentDateTime = LocalDateTime.now();
            }
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            }

            String currentDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentDate = currentDateTime.format(formatter);
            }

            observation.setObservationTitle(observationTitle);
            observation.setHikeId(hikeId);
            observation.setObservationDate(currentDate);
            observation.setComment(observationComment);

            Log.d("ADD_OBSERVATION_ACTIVITY", observation.toString());

            dbHelper.saveObservation(observation);

            editTextComments.setText("");
            editTextObservationTitle.setText("");
            imageViewCapturedPhoto.setVisibility(View.GONE);

            Toast.makeText(this, "Observation added successfully!", Toast.LENGTH_SHORT).show();
        }

    }
}