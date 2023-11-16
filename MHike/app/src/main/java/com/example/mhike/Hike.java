package com.example.mhike;

import java.io.Serializable;

public class Hike implements Serializable {
    private int _id;
    private String name;
    private String location;
    private String date;
    private boolean parkingStatus;
    private double length; // in miles or kilometers
    private String difficulty;
    private String description;
    private int elevation; // in feet or meters
    private String timeToComplete; // in hours and minutes

    public Hike() {
        // Default constructor
    }

    public Hike(String name, String location, String date, boolean parkingStatus,
                double length, String difficulty, String description, int elevation, String timeToComplete) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.parkingStatus = parkingStatus;
        this.length = length;
        this.difficulty = difficulty;
        this.description = description;
        this.elevation = elevation;
        this.timeToComplete = timeToComplete;
    }

    // Getters and setters for each field
    public int getId() { return _id; };

    public void setId(int id) { this._id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(boolean parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public String getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(String timeToComplete) {
        this.timeToComplete = timeToComplete;
    }
}
