package com.cabinvoicegenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CabInvoiceGeneratorTest {

    @Test
    public void givenDistanceAndTime_ShouldReturnInvoiceSummary() {
       InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        double distance = 2.0;
        int time = 5;
        double fare = invoiceGenerator.calculateFare(distance, time);
        Assertions.assertEquals(25, fare, 0.0);
    }

    @Test
    public void givenLessDistanceAndTime_ShouldReturnMinimumFare() {
       InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        double distance = 0.1;
        int time = 5;
        double minimumFare = invoiceGenerator.calculateFare(distance, time);
        Assertions.assertEquals(5, minimumFare, 0.0);
    }

    @Test
    public void givenMultipleRides_ShouldReturnTotalFare() {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        Ride[] rides = { new Ride(2.0, 5, InvoiceGenerator.RideType.NORMALRIDE),
                         new Ride(0.1, 1, InvoiceGenerator.RideType.NORMALRIDE)
                        };
        //double fare = invoiceGenerator.calculateFare(rides);
        //Assertions.assertEquals(30, fare, 0.0);
        InvoiceSummary summary = invoiceGenerator.calculateFare(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
        Assertions.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenUserId_ShouldReturnInvoiceSummary() throws InvoiceGeneratorException {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        String[] userId = {"user1", "user2", "user3"};
        Ride[][] rides ={
                {new Ride(5.0, 12, InvoiceGenerator.RideType.NORMALRIDE), new Ride(2.5, 6, InvoiceGenerator.RideType.NORMALRIDE)},
                {new Ride(3.0, 5, InvoiceGenerator.RideType.NORMALRIDE), new Ride(0.01, 1, InvoiceGenerator.RideType.NORMALRIDE)},
                {new Ride(10.0, 15, InvoiceGenerator.RideType.NORMALRIDE), new Ride(2, 30, InvoiceGenerator.RideType.NORMALRIDE)} };
        invoiceGenerator.addRideToRepositoy(userId, rides);
        InvoiceSummary summery = invoiceGenerator.invoiceForUser(userId[2]);
        InvoiceSummary expectedInvoiceSummery = new InvoiceSummary(rides[2].length, 165.0);
        Assertions.assertEquals(expectedInvoiceSummery, summery);
    }

    @Test
    public void givenUserId_ShouldReturnInvoiceSummaryOfTwoDifferentRide() throws InvoiceGeneratorException {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        String[] userId = {"user1", "user2", "user3"};
        Ride[][] rides ={
                {new Ride(5.0, 12, InvoiceGenerator.RideType.NORMALRIDE), new Ride(2.5, 6, InvoiceGenerator.RideType.NORMALRIDE)},
                {new Ride(3.0, 5, InvoiceGenerator.RideType.NORMALRIDE), new Ride(0.01, 1, InvoiceGenerator.RideType.PREMIUMRIDE)},
                {new Ride(10.0, 15, InvoiceGenerator.RideType.PREMIUMRIDE), new Ride(2, 30, InvoiceGenerator.RideType.PREMIUMRIDE)} };
        invoiceGenerator.addRideToRepositoy(userId, rides);
        InvoiceSummary summery = invoiceGenerator.invoiceForUser(userId[2]);
        InvoiceSummary expectedInvoiceSummery = new InvoiceSummary(rides[2].length, 205.0);
        Assertions.assertEquals(expectedInvoiceSummery, summery);
    }
}