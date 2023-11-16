package com.example.logbookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageViewer extends AppCompatActivity {

    private ImageView imageView;
    private Button buttonForward;
    private Button buttonBackward;
    private int currentImageIndex = 0;
    private int[] imageResources = {
            android.R.drawable.btn_dialog,
            R.drawable.ic_launcher_foreground,
            android.R.drawable.alert_dark_frame,
            android.R.drawable.bottom_bar
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        imageView = findViewById(R.id.imageView);
        buttonForward = findViewById(R.id.buttonForward);
        buttonBackward = findViewById(R.id.buttonBackward);

        displayImage(currentImageIndex);

        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentImageIndex < imageResources.length - 1) {
                    currentImageIndex++;
                    displayImage(currentImageIndex);
                }
            }
        });

        buttonBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentImageIndex > 0) {
                    currentImageIndex--;
                    displayImage(currentImageIndex);
                }
            }
        });
    }

    private void displayImage(int index) {
        imageView.setImageResource(imageResources[index]);
    }
}