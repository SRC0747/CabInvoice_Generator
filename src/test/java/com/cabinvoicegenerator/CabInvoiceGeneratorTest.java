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
        int time = 1;
        double minimumFare = invoiceGenerator.calculateFare(distance, time);
        Assertions.assertEquals(5, minimumFare, 0.0);
    }

    @Test
    public void givenMultipleRides_ShouldReturnTotalFare() {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        Ride[] rides = { new Ride(2.0, 5, InvoiceService.RideMode.NORMAL),
                         new Ride(0.1, 1, InvoiceService.RideMode.NORMAL)
                        };
        InvoiceSummary summary = invoiceGenerator.calculateFare(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
        Assertions.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenUserId_ShouldReturnInvoiceSummary() throws InvoiceGeneratorException {
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        String[] userId = {"user1", "user2", "user3"};
        Ride[][] rides ={
                {new Ride(5.0, 12, InvoiceService.RideMode.NORMAL), new Ride(2.5, 6, InvoiceService.RideMode.NORMAL)},
                {new Ride(3.0, 5, InvoiceService.RideMode.NORMAL), new Ride(0.01, 1, InvoiceService.RideMode.NORMAL)},
                {new Ride(10.0, 15, InvoiceService.RideMode.NORMAL), new Ride(2, 30, InvoiceService.RideMode.NORMAL)} };
        invoiceGenerator.addRideToRepositoy(userId, rides);
        InvoiceSummary summery = invoiceGenerator.invoiceForUser(userId);
        InvoiceSummary expectedInvoiceSummery = new InvoiceSummary(rides[2].length, 165.0);
        Assertions.assertEquals(expectedInvoiceSummery, summery);
    }

    @Test
    public void givenDistanceAndTime_shouldReturnPremiumTotalFare() {
        InvoiceService invoiceService = new InvoiceService();
        double distance = 2.0;
        int time = 5;
        double fare = invoiceService.calculateFareForPremium(distance, time);
        Assertions.assertEquals(40, fare, 0.0);
    }

    @Test
    public void givenMultipleRide_ShouldReturnPremiumTotalFare() {
        InvoiceService invoiceService = new InvoiceService();
        Ride[] rides = {new Ride(2.0, 5, InvoiceService.RideMode.PREMIUM),
                new Ride(0.1, 1,InvoiceService.RideMode.PREMIUM),
        };
        InvoiceSummary summary = invoiceService.calculateFareForNormal(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 50);
        Assertions.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnBothInvoiceSummary() throws InvoiceGeneratorException {
        InvoiceService invoiceService = new InvoiceService();
        String[] userId = {"user1", "user2", "user3"};
        Ride[][] rides ={
                {new Ride(5.0, 12, InvoiceService.RideMode.NORMAL), new Ride(2.5, 6, InvoiceService.RideMode.PREMIUM)},
                {new Ride(3.0, 5, InvoiceService.RideMode.PREMIUM), new Ride(0.01, 1, InvoiceService.RideMode.NORMAL)},
                {new Ride(10.0, 15, InvoiceService.RideMode.NORMAL), new Ride(2, 30, InvoiceService.RideMode.PREMIUM)} };
        invoiceService.addRides(userId, rides);
        InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 111.5);
        Assertions.assertEquals(expectedInvoiceSummary, summary);
    }
}