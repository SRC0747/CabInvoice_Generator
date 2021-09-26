package com.cabinvoicegenerator;

public class Ride extends InvoiceGenerator{
    public final double distance;
    public final int time;
    public RideType rideType;

    public Ride(double distance, int time, RideType rideType) {
        this.distance = distance;
        this.time = time;
        this.rideType = rideType;
    }
}
