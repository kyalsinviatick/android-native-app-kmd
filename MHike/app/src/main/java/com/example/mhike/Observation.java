package com.example.mhike;

import java.io.Serializable;
import java.util.Date;

public class Observation implements Serializable {
    private long _id;
    private String observationTitle;
    private long hikeId;
    private String observationDate;
    private String photo;
    private String comment;

    // Constructors

    public Observation() {
        // Default constructor
    }

    public Observation(String observationTitle, long hikeId, String observationDate, String comment, String photo) {
        this.observationTitle = observationTitle;
        this.hikeId = hikeId;
        this.observationDate = observationDate;
        this.photo = photo;
        this.comment = comment;
    }

    // Getter and Setter methods

    public long getId() { return _id; };

    public void setId(long id) { this._id = id; }

    public String getObservationTitle() {
        return observationTitle;
    }

    public void setObservationTitle(String observationTitle) {
        this.observationTitle = observationTitle;
    }

    public long getHikeId() {
        return hikeId;
    }

    public void setHikeId(long hikeId) {
        this.hikeId = hikeId;
    }

    public String getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(String observationDate) {
        this.observationDate = observationDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // toString method
    @Override
    public String toString() {
        return "Observation{" +
                "observationTitle='" + observationTitle + '\'' +
                ", hikeId=" + hikeId +
                ", observationDate=" + observationDate +
                ", photo='" + photo + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}