package com.cabinvoicegenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CabInvoiceGeneratorTest {

    @Test
    public void givenDistanceAndTime_ShouldReturnTotalFare() {
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
}