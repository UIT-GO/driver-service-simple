package com.example.trip_service_simple.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Trip {
    @Id
    private String id;
    private String userId;
    private String status;
    private String driverId;
    private String startLocation;
    private String endLocation;
}
