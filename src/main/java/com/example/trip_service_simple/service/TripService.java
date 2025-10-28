package com.example.trip_service_simple.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface TripService {
    String createTrip(String userId, String startLocation, String endLocation) throws JsonProcessingException;
}
