package com.example.mhike;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ObservationViewHolder> {

    private List<Observation> observationList;
    private Context context;
    private ObservationDBHelper dbHelper;

    public ObservationAdapter(Context context, List<Observation> observationList) {
        this.context = context;
        this.observationList = observationList;
    }

    @Override
    public ObservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_observation, parent, false);

        return new ObservationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ObservationViewHolder holder, int position) {
        Observation observation = observationList.get(position);

        dbHelper = new ObservationDBHelper(context);

        Log.d("OBSERVATION ADAPTER", observation.toString());

        // Load and display the photo using Glide
        if (observation.getPhoto() != null && !observation.getPhoto().isEmpty()) {
            Glide.with(context)
                    .load(new File(observation.getPhoto())) // Use the file path
                    .into(holder.imageViewPhoto);
        } else {
            // If no photo is available, you might want to set a default image or hide the ImageView
            holder.imageViewPhoto.setImageResource(R.drawable.ic_launcher_background);
        }

        // Bind data to the views
        holder.titleTextView.setText(observation.getObservationTitle());
        holder.dateTextView.setText(observation.getObservationDate());
        holder.commentTextView.setText(observation.getComment());
        // Add other views as needed

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditObservationActivity.class);
                intent.putExtra("observationObject", observation);
                context.startActivity(intent);
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want to proceed?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // User clicked Yes, perform your action
                                dbHelper.deleteObservation(observation.getId());
                                ((Activity)context).finish();
                                context.startActivity(((Activity)context).getIntent());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // User clicked No, do nothing or handle accordingly
                            }
                        })
                        .show();
            }
        });


        // Add click listener if needed
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle item click
                // You can navigate to another activity or perform other actions here
            }
        });
    }

    @Override
    public int getItemCount() {
        return observationList.size();
    }

    public class ObservationViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView dateTextView;
        public TextView commentTextView;

        public ImageView imageViewPhoto;

        public Button buttonEdit, buttonDelete;
        // Add other views as needed

        public ObservationViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.textViewTitle);
            dateTextView = view.findViewById(R.id.textViewDate);
            commentTextView = view.findViewById(R.id.textViewComments);
            buttonEdit = view.findViewById(R.id.buttonEdit);
            buttonDelete = view.findViewById(R.id.buttonDelete);
            imageViewPhoto = view.findViewById(R.id.imageViewPhoto);
            // Initialize other views
        }
    }
}
