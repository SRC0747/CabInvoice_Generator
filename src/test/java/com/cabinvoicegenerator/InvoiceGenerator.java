package com.cabinvoicegenerator;

public class InvoiceGenerator {
    private static final double NORMAL_MINIMUM_COST_PER_KILLOMETER = 10;
    private static final int NORMAL_COST_PER_TIME = 1;
    private static final double NORMAL_MINIMUM_FARE = 5;
    private static final double PREMIUM_MINIMUM_COST_PER_KILLOMETER = 15.0;
    private static final int PREMIUM_COST_PER_TIME = 2;
    private static final double PREMIUM_MINIMUM_FARE = 20;
    private int time;
    private double distance;
    RideRepository rideRepository;
    public InvoiceGenerator()
    {
        rideRepository = new RideRepository();
    }
    public enum RideType { NORMALRIDE, PREMIUMRIDE }
    public  RideType rideType;

    InvoiceGenerator(double distance, int time, RideType rideType){
        this.distance = distance;
        this.time = time;
        this.rideType = rideType;
    }

    public double calculateFare(double distance, int time) {
        double totalFare_NORMALRIDE = distance * NORMAL_MINIMUM_COST_PER_KILLOMETER + time * NORMAL_COST_PER_TIME;
        //double totalFare_PREMIUMRIDE = distance * PREMIUM_MINIMUM_COST_PER_KILLOMETER + time * PREMIUM_COST_PER_TIME;
        return Math.max(totalFare_NORMALRIDE, NORMAL_MINIMUM_FARE);
        //return Math.max(totalFare_PREMIUMRIDE, PREMIUM_MINIMUM_FARE);
    }

    public InvoiceSummary calculateFare(Ride[] rides) {
        double totalFare = 0;
        for (Ride ride:rides){
            totalFare = totalFare + this.calculateFare(ride.distance, ride.time);
        }
        return new InvoiceSummary(rides.length, totalFare);
    }

    public void addRideToRepositoy(String[] userId, Ride[][] rides) throws InvoiceGeneratorException {
        for (int i = 0; i < userId.length; i++)
        {
            rideRepository.addRideForUser(userId[i], rides[i]);
        }
    }

    public InvoiceSummary invoiceForUser(String userId) {
        return calculateFare(rideRepository.getRidesForUser(userId));
    }
}
