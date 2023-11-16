package com.example.mhike;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.HikeViewHolder> implements Filterable {

    private List<Hike> hikeList;
    private List<Hike> filteredHikeList;
    private Context context;

    private HikeDBHelper dbHelper;

    public HikeAdapter(Context context, List<Hike> hikeList) {
        this.hikeList = hikeList;
        this.context = context;
        this.filteredHikeList = new ArrayList<>(hikeList);
        dbHelper = new HikeDBHelper(context);
    }

    @NonNull
    @Override
    public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_hike, parent, false);
        return new HikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeViewHolder holder, int position) {
        Hike hike = filteredHikeList.get(position);

        holder.textViewName.setText("Name: " + hike.getName());
        holder.textViewLocation.setText("Location: " + hike.getLocation());
        holder.textViewDate.setText("Date: " + hike.getDate());

        holder.viewHikeDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewHikeDetailActivity.class);
                intent.putExtra("hikeId", hike.getId());
                context.startActivity(intent);
            }
        });

        holder.editHikeDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditHikeActivity.class);
                intent.putExtra("hikeObject", hike);
                context.startActivity(intent);
            }
        });

        holder.deleteHikeDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(hike);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredHikeList.size();
    }

    static class HikeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewLocation;
        TextView textViewDate;
        Button viewHikeDetailButton;
        Button editHikeDetailButton, deleteHikeDetailButton;

        HikeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            viewHikeDetailButton = itemView.findViewById(R.id.buttonViewHikeDetail);

            editHikeDetailButton = itemView.findViewById(R.id.buttonEdit);
            deleteHikeDetailButton = itemView.findViewById(R.id.buttonDelete);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase().trim();
                List<Hike> filteredList = new ArrayList<>();

                Log.d("HIKE ADAPTER", query);

                if (query.isEmpty()) {
                    filteredList.addAll(hikeList);
                } else {
                    for (Hike hike : hikeList) {
                        if (hike.getName().toLowerCase().contains(query)
                                || hike.getLocation().toLowerCase().contains(query)) {
                            filteredList.add(hike);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                Log.d("HIKE ADAPTER", filterResults.toString());
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredHikeList.clear();
                filteredHikeList.addAll((List<Hike>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    private void showDeleteConfirmationDialog(Hike hike) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Confirmation")
                .setMessage("Are you sure you want to proceed?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteHike(hike.getId());
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked No, do nothing or handle accordingly
                    }
                })
                .show();
    }
}


// HikeAdapter.java

//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.HikeViewHolder> {
//
//    private List<Hike> hikeList;
//    private List<Hike> filteredHikeList;
//    private Context context;
//
//    private HikeDBHelper dbHelper;
//
//    public HikeAdapter(Context context, List<Hike> hikeList) {
//        this.hikeList = hikeList;
//        this.context = context;
//        this.filteredHikeList = new ArrayList<>(hikeList);
//    }
//
//    @NonNull
//    @Override
//    public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_hike, parent, false);
//        return new HikeViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull HikeViewHolder holder, int position) {
//        Hike hike = hikeList.get(position);
//
//        dbHelper = new HikeDBHelper(context);
//
//        Log.d("HIKE ADAPTER", hike.getDate().toString());
//
//        holder.textViewName.setText("Name: " + hike.getName());
//        holder.textViewLocation.setText("Location: " + hike.getLocation());
//        holder.textViewDate.setText("Date: " + hike.getDate());
//
//        holder.viewHikeDetailButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Handle button click, e.g., navigate to a new activity
//                Intent intent = new Intent(context, ViewHikeDetailActivity.class);
//                intent.putExtra("hikeId", hike.getId()); // Pass the hike ID to the detail activity
//                context.startActivity(intent);
//            }
//        });
//
//        holder.editHikeDetailButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, EditHikeActivity.class);
//                intent.putExtra("hikeObject", hike);
//                context.startActivity(intent);
//            }
//        });
//
//        holder.deleteHikeDetailButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                builder.setTitle("Confirmation")
//                        .setMessage("Are you sure you want to proceed?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // User clicked Yes, perform your action
//                                dbHelper.deleteHike(hike.getId());
//                                ((Activity)context).finish();
//                                context.startActivity(((Activity)context).getIntent());
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // User clicked No, do nothing or handle accordingly
//                            }
//                        })
//                        .show();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return hikeList.size();
//    }
//
//    static class HikeViewHolder extends RecyclerView.ViewHolder {
//        TextView textViewName;
//        TextView textViewLocation;
//        TextView textViewDate;
//        Button viewHikeDetailButton;
//
//        Button editHikeDetailButton, deleteHikeDetailButton;
//
//        HikeViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textViewName = itemView.findViewById(R.id.textViewName);
//            textViewLocation = itemView.findViewById(R.id.textViewLocation);
//            textViewDate = itemView.findViewById(R.id.textViewDate);
//            viewHikeDetailButton = itemView.findViewById(R.id.buttonViewHikeDetail);
//
//            editHikeDetailButton = itemView.findViewById(R.id.buttonEdit);
//            deleteHikeDetailButton = itemView.findViewById(R.id.buttonDelete);
//        }
//    }
//}
