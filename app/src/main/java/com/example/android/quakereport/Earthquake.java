package com.example.android.quakereport;

public class Earthquake {
    private String date;
    private String location;
    private double magnitude;

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public double getMagnitude() {
        return magnitude;
    }

    /**
     * Constructor for the Earthquake class
     * @param location is location of the earthquake
     * @param date is the time when it occurred
     * @param magnitude is the magnitude
     */
    public Earthquake(double magnitude, String location, String date) {
        this.date = date;
        this.location = location;
        this.magnitude = magnitude;
    }

    public Earthquake() {
    }
}
