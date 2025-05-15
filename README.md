# Error Handling in Java 21

## Project Overview

This Spring Boot application demonstrates best practices for error handling in Java 21. It provides an HTTP endpoint
that retrieves reports for users, showcasing various error handling techniques and exception management strategies.

## Features

- RESTful API for retrieving reports
- Comprehensive error handling with custom exceptions
- Simulated database and external service interactions
- OpenAPI documentation with SwaggerUI

## Architecture

The application follows a layered architecture:

- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic and error handling
- **Repository Layer**: Simulates database access
- **External Client**: Simulates interaction with an external API

## API Endpoints

The application exposes the following endpoint:

### Get Report

```
GET /report?userId={userId}&reportName={reportName}
```

- **Parameters**:
    - `userId`: The ID of the user requesting the report
    - `reportName`: The name of the report to retrieve
- **Response**: A Report object containing the report data
- **Error Responses**:
    - 404: User not found in database
    - 404: Report name not found in report API
    - 404: User not found in report API
    - 500: Database access error
    - 500: Unexpected error from report client

## Error Handling Strategy

The application demonstrates several error handling techniques:

1. **Custom Exceptions**: Domain-specific exceptions for different error scenarios
2. **Exception Translation**: Converting low-level exceptions to more meaningful ones
3. **Proper HTTP Status Codes**: Mapping exceptions to appropriate HTTP status codes
4. **Informative Error Messages**: Clear error messages for troubleshooting
5. **Problem Details for HTTP APIs**: Implementation of [RFC 7807](https://datatracker.ietf.org/doc/html/rfc7807)
   standard for error responses

### Problem Details API

This application implements the Problem Details for HTTP APIs specification (RFC 7807) using Spring's `ProblemDetail`
class. This provides a standardized format for error responses, making them more consistent, machine-readable, and
developer-friendly.

Key aspects of our implementation:

- **Standardized JSON Structure**: All error responses follow the RFC 7807 format with properties like `type`, `title`,
  `status`, `detail`, and `instance`
- **Type URIs**: Each error category has a unique URI identifier (e.g.,
  `https://api.error-handling.com/problems/user-not-found`)
- **Consistent Error Handling**: The `ErrorHandler` class centralizes all exception handling and converts exceptions to
  appropriate Problem Detail responses
- **Descriptive Error Messages**: Each error includes a human-readable description to aid troubleshooting

Benefits of using Problem Details:

- Improved API documentation and discoverability
- Better client error handling with machine-readable responses
- Consistent error format across the entire API
- Enhanced debugging capabilities

## Running the Application

1. Clone the repository
2. Run `mvn spring-boot:run`
3. Access the API at `http://localhost:8080/report?userId={userId}&reportName={reportName}`
4. Access the SwaggerUI documentation at `http://localhost:8080/swagger-ui.html`

## Implementation Details

The application flow:

1. The controller receives a request with userId and reportName
2. The UserService retrieves the user from the database (simulated)
3. The ReportService uses the user's email to retrieve the report from an external API (simulated)
4. Various exceptions may be thrown during this process, which are handled appropriately

## Technologies Used

- Java 21
- Spring Boot 3.4.5
- SpringDoc OpenAPI UI
- Spotless (code formatting)
- SpotBugs (code quality checking)

## Code Quality Tools

This project uses the following code quality tools:

### Spotless

Spotless is used for code formatting and ensuring consistent code style across the project. It's configured to use Google's Java Style Guide.

Key features:
- Formats Java code according to Google's Java Style Guide
- Removes unused imports
- Orders imports consistently
- Runs during the compile phase

To run Spotless manually:
```
mvn spotless:check    # Check for formatting issues
mvn spotless:apply    # Fix formatting issues
```

### SpotBugs

SpotBugs is used for static code analysis to find potential bugs and code quality issues.

Key features:
- Detects potential bugs and code quality issues
- Includes FindSecBugs plugin for security vulnerability detection
- Runs during the verify phase
- Fails the build if issues are found

To run SpotBugs manually:
```
mvn spotbugs:check    # Run SpotBugs analysis
mvn spotbugs:gui      # Open SpotBugs GUI to view issues
```
