package com.example.doctor_client_app;

public class Hospital {
    String Name;
    String Longitude;
    String Latitude;

    public Hospital(String name, String Longitude, String Latitude){
        this.Name=name;
        this.Longitude=Longitude;
        this.Latitude=Latitude;
    }

    public String getName() {
        return Name;
    }
    public String getLongitude() {
        return Longitude;
    }
    public String getLatitude() {
        return Latitude;
    }
}
