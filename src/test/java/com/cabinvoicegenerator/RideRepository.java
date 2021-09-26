package com.cabinvoicegenerator;

import java.util.*;

public class RideRepository {
    Map<String, Ride[]> userRides = new HashMap<>();

    public Ride[] getRidesForUser(String userId) {
        return userRides.get(userId);
    }

    public void addRideForUser(String userId, Ride[] ride) throws InvoiceGeneratorException {
        if (userRides.containsKey(userId))
            throw new InvoiceGeneratorException(InvoiceGeneratorException.ExceptionType.USER_ALREADY_EXISTS,
                    "User ID Already Exists!!!");
        userRides.put(userId, ride);
    }
}
