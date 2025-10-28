# Trip Service Simple

A simple Spring Boot microservice for trip management, using Kafka for event streaming and WebSocket for real-time frontend updates.

## Features
- Create and manage trips
- Kafka integration for trip events
- WebSocket notifications to frontend
- MongoDB for data storage

## Getting Started

### Prerequisites
- Java 17+
- Maven
- MongoDB running on `localhost:27017` (default user: `admin`, password: `admin123`, database: `tripdb`)
- Kafka running on `localhost:29092`

### Build & Run
```bash
mvn clean install
mvn spring-boot:run
```

### Configuration
Edit `src/main/resources/application.properties` for MongoDB and Kafka settings.

### WebSocket Frontend
Open `src/main/resources/static/index.html` in your browser. Enter your userId when prompted to receive real-time trip status updates.

## API
- `POST /api/trips` - Create a new trip (accepts raw JSON: `userId`, `startLocation`, `endLocation`)

## Kafka Topics
- `trip_created` - Used for trip creation events

## WebSocket Endpoints
- `/ws` - SockJS endpoint
- `/user/{userId}/queue/hide-spinner` - Receives trip status updates

## License
MIT

