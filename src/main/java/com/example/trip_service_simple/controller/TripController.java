package com.example.trip_service_simple.controller;

import com.example.trip_service_simple.request.CreateTripRequest;
import com.example.trip_service_simple.service.TripService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping()
    public ResponseEntity<String> createTrip(@RequestBody CreateTripRequest createTripRequest) throws JsonProcessingException {
        return ResponseEntity.ok(tripService.createTrip(createTripRequest.getUserId(), createTripRequest.getStartLocation(), createTripRequest.getEndLocation()));
    }
}
