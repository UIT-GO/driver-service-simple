package com.example.trip_service_simple.service;

import com.example.trip_service_simple.event.CreateTripEvent;
import com.example.trip_service_simple.model.Trip;
import com.example.trip_service_simple.repository.TripRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.ObjectInput;

@Service
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TRIP_CREATED_TOPIC = "trip_create_wait_driver";
    private final SimpMessagingTemplate messagingTemplate;

    public TripServiceImpl(TripRepository tripRepository, KafkaTemplate<String, String> kafkaTemplate, SimpMessagingTemplate messagingTemplate) {
        this.tripRepository = tripRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public String createTrip(String userId, String startLocation, String endLocation) throws JsonProcessingException {
        Trip trip = new Trip();

        trip.setUserId(userId);
        trip.setStartLocation(startLocation);
        trip.setEndLocation(endLocation);
        trip.setStatus("CREATED");

        Trip createdTrip = tripRepository.save(trip);

        CreateTripEvent createTripEvent = new CreateTripEvent();
        createTripEvent.setUserId(userId);
        createTripEvent.setStartLocation(startLocation);
        createTripEvent.setEndLocation(endLocation);
        createTripEvent.setTripId(createdTrip.getId());

        //Serialize and send trip created event to Kafka
        String json = new ObjectMapper().writeValueAsString(createTripEvent);
        kafkaTemplate.send(TRIP_CREATED_TOPIC, json);

        //Use websocket to notify user about trip creation
        messagingTemplate.convertAndSend("/topic/create-trip/" + userId,
                "Trip created with ID: " + trip.getId());
        System.out.println(userId);
        return "Created trip with ID: " + tripRepository.save(trip).getId();
    }
}
