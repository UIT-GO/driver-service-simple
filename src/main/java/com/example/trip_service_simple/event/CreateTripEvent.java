package com.example.trip_service_simple.event;

import lombok.Data;

@Data
public class CreateTripEvent {
    private String userId;
    private String startLocation;
    private String endLocation;
    private String tripId;
}
