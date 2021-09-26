package com.cabinvoicegenerator;

public class InvoiceGenerator {

    private static final double MINIMUM_COST_PER_KILLOMETER = 10;
    private static final int COST_PER_TIME = 1;
    private static final double MINIMUM_FARE = 5;
    RideRepository rideRepository;
    public InvoiceGenerator()
    {
        rideRepository = new RideRepository();
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
