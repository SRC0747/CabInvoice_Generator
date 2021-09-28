package com.cabinvoicegenerator;

public class Ride{
    public final int time;
    public final double distance;
    public final InvoiceService.RideMode rideMode;

    public Ride(double distance, int time, InvoiceService.RideMode rideMode) {
        this.distance = distance;
        this.time = time;
        this.rideMode = rideMode;
    }
}
