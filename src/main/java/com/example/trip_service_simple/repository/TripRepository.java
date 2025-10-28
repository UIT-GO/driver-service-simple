package com.example.trip_service_simple.repository;

import com.example.trip_service_simple.model.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TripRepository extends MongoRepository<Trip, String> {
}
