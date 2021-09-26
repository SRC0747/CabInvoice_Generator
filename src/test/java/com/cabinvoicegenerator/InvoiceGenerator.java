package com.cabinvoicegenerator;

public class InvoiceGenerator {

    private static final double NORMAL_MINIMUM_COST_PER_KILLOMETER = 10.0;
    private static final int NORMAL_COST_PER_TIME = 1;
    private static final double NORMAL_MINIMUM_FARE =5;
    private static final double PREMIUM_MINIMUM_COST_PER_KILLOMETER = 15.0;
    private static final int PREMIUM_COST_PER_TIME = 2;
    private static final double PREMIUM_MINIMUM_FARE = 20;

    RideRepository rideRepository;
    public InvoiceGenerator()
    {
        rideRepository = new RideRepository();
    }

    public enum RideType { NORMALRIDE, PREMIUMRIDE }
    public  RideType ridetype;


    InvoiceGenerator(double NORMAL_MINIMUM_COST_PER_KILLOMETER, int NORMAL_COST_PER_TIME, RideType rideType){
        this.NORMAL_MINIMUM_COST_PER_KILLOMETER = NORMAL_MINIMUM_COST_PER_KILLOMETER;
        this.NORMAL_COST_PER_TIME = NORMAL_COST_PER_TIME;
        this.ridetype = rideType;
    }
    InvoiceGenerator(double PREMIUM_MINIMUM_COST_PER_KILLOMETER, int PREMIUM_COST_PER_TIME, RideType rideType){
        this.PREMIUM_MINIMUM_COST_PER_KILLOMETER = PREMIUM_MINIMUM_COST_PER_KILLOMETER;
        this.PREMIUM_COST_PER_TIME = PREMIUM_COST_PER_TIME;
        this.ridetype = rideType;
    }
    public double calculateFare(double distance, int time) {
        double totalFare_NormalRide = distance * NORMAL_MINIMUM_COST_PER_KILLOMETER + time * NORMAL_COST_PER_TIME;
        double totalFare_PremiumRide = distance * PREMIUM_MINIMUM_COST_PER_KILLOMETER + time * PREMIUM_COST_PER_TIME;
        //if(totalFare < MINIMUM_FARE)
           // return MINIMUM_FARE;
        return Math.max(totalFare_NormalRide, NORMAL_MINIMUM_FARE);
        return Math.max(totalFare_PremiumRide, PREMIUM_MINIMUM_FARE);
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
