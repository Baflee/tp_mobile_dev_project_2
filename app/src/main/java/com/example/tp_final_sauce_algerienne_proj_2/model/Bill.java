package com.example.tp_final_sauce_algerienne_proj_2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bill {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("userId")
    @Expose
    private int userId;

    @SerializedName("rider")
    @Expose
    private String rider;

    @SerializedName("rideAmount")
    @Expose
    private double rideAmount;

    @SerializedName("riderAmount")
    @Expose
    private double riderAmount;

    @SerializedName("duration")
    @Expose
    private int duration;

    @SerializedName("dueDate")
    @Expose
    private String dueDate;

    @SerializedName("origin")
    @Expose
    private String origin;

    @SerializedName("destination")
    @Expose
    private String destination;

    @SerializedName("distance")
    @Expose
    private double distance;

    public Bill(int userId, String rider, double rideAmount, double riderAmount, int duration, String dueDate, String origin, String destination, double distance) {
        this.userId = userId;
        this.rider = rider;
        this.rideAmount = rideAmount;
        this.riderAmount = riderAmount;
        this.duration = duration;
        this.dueDate = dueDate;
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
    }

    // Getter and setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and setter for userId
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter and setter for dueDate
    public String getRider() {
        return rider;
    }

    public void setRider(String rider) {
        this.rider = rider;
    }

    // Getter and setter for amounts
    public double getRiderAmount() {
        return riderAmount;
    }

    public void setRiderAmount(double riderAmount) {
        this.riderAmount = riderAmount;
    }

    public double getRideAmount() {
        return rideAmount;
    }

    public void setRideAmount(double rideAmount) {
        this.rideAmount = rideAmount;
    }

    // Getter and setter for duration
    public double getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    // Getter and setter for dueDate
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    // Getter and setter for origin
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    // Getter and setter for destination
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    // Getter and setter for distance
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
