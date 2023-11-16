package com.example.contactdb;

public class Contact {
    private long _id;
    private String name;
    private String email;
    private String dob;

    public Contact() {
    }

    public Contact(String name, String email, String dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}