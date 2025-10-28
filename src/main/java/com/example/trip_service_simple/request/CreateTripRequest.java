package com.example.trip_service_simple.request;

import lombok.Data;

@Data
public class CreateTripRequest {
    private String userId;
    private String startLocation;
    private String endLocation;
}
