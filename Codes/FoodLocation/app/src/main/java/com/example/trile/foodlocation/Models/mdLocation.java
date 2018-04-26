package com.example.trile.foodlocation.Models;

/**
 * Created by TRILE on 25/04/2018.
 */

public class mdLocation {
    private String name;
    public double latitude;
    public double longitude;

    public mdLocation() {
        //
    }

    public mdLocation(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
