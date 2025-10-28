package com.example.trip_service_simple.kafkaListener;

import com.example.trip_service_simple.event.CreateTripEvent;
import com.example.trip_service_simple.event.CreatedTripEvent;
import com.example.trip_service_simple.model.Trip;
import com.example.trip_service_simple.repository.TripRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class CreatedTripListener {
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @KafkaListener(topics = "trip_created", groupId = "driver-service-group")
    public void listenTripCreated(String message) {
        try {
            CreatedTripEvent event = objectMapper.readValue(message, CreatedTripEvent.class);
            // Process the event (e.g., log, update DB, etc.)
            System.out.println("Received trip event: " + event);

            // add websocket notification logic here if needed
            Trip trip = tripRepository.findById(event.getTripId()).orElse(null);
            if (trip != null) {
                trip.setDriverId(event.getDriverId());
                trip.setStatus("ACCEPTED");
                Trip createdTrip = tripRepository.save(trip);

                simpMessagingTemplate.convertAndSend(
                        "/topic/hide-spinner/" + trip.getUserId(),
                        objectMapper.writeValueAsString(createdTrip)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
