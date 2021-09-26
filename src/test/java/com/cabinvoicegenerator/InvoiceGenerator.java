package com.cabinvoicegenerator;

public class InvoiceGenerator {

    private static  double MINIMUM_COST_PER_KILLOMETER ;
    private static int COST_PER_TIME;
    private static  double MINIMUM_FARE;
    RideRepository rideRepository;
    public InvoiceGenerator()
    {
        rideRepository = new RideRepository();
    }

    public enum RideType { NORMALRIDE, PREMIUMRIDE }
    public  RideType ridetype;

    InvoiceGenerator(double MINIMUM_COST_PER_KILLOMETER, int COST_PER_TIME, RideType rideType){
        this.MINIMUM_COST_PER_KILLOMETER = MINIMUM_COST_PER_KILLOMETER;
        this.COST_PER_TIME = COST_PER_TIME;
        this.ridetype = rideType;
    }
    public double calculateFare(double distance, int time) {
        double totalFare = distance * MINIMUM_COST_PER_KILLOMETER + time * COST_PER_TIME;
        //if(totalFare < MINIMUM_FARE)
           // return MINIMUM_FARE;
        return Math.max(totalFare, MINIMUM_FARE);
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
