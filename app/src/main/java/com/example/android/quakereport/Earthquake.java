package com.example.android.quakereport;

public class Earthquake {
    private String date;
    private String location;
    private double magnitude;
    private String url;

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public double getMagnitude() {
        return magnitude;
    }
    public String getUrl(){return url;}

    /**
     * Constructor for the Earthquake class
     * @param location is location of the earthquake
     * @param date is the time when it occurred
     * @param magnitude is the magnitude
     */
    public Earthquake(double magnitude, String location, String date, String url) {
        this.date = date;
        this.location = location;
        this.magnitude = magnitude;
        this.url = url;
    }

    public Earthquake() {
    }
}
