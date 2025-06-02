# Tags API Migration

This directory contains the Java implementation of the `/api/tags` endpoint, migrated from Scala/Akka to Java/Spring Boot.

## Implementation Details

The implementation follows the Spring Boot architecture with the following components:

- **Model**: `Tag.java` - JPA entity representing a tag in the database
- **Repository**: `TagRepository.java` - Spring Data JPA repository for database operations
- **Service**: `TagService.java` - Business logic layer
- **Controller**: `TagController.java` - REST API endpoint
- **DTO**: `TagsResponse.java` - Data Transfer Object for API responses

## API Endpoint

The API provides the following endpoint:

- `GET /api/tags` - Returns a list of all tags in the system

## Running the Application

To run the application:

```bash
mvn spring-boot:run
```

## Testing

The implementation includes unit tests for both the service and controller layers.

## Migration Notes

- The original Scala implementation used Slick for database access, which has been replaced with Spring Data JPA
- The original Akka HTTP routes have been replaced with Spring MVC controllers
- The response format remains the same, ensuring API compatibility