package com.tws.lab.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tws.lab.model.Car;
import com.tws.lab.rest.error.RestClientException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class CarRestClient {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";
    private final String baseUrl;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CarRestClient(String baseUrl) {
        // Remove trailing slash if present
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    private String getBasicAuthHeader() {
        String auth = USERNAME + ":" + PASSWORD;
        return "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
    }

    public List<Car> searchCars(String query, int limit, int offset) throws RestClientException {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            URI uri = URI.create(String.format("%s/api/cars/search?query=%s&limit=%d&offset=%d", baseUrl, encodedQuery, limit, offset));
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return Arrays.asList(objectMapper.readValue(response.body(), Car[].class));
            } else {
                throw new RestClientException("Error searching cars: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RestClientException("Error searching cars: " + e.getMessage());
        }
    }

    public Car findById(int id) throws RestClientException {
        try {
            URI uri = URI.create(baseUrl + "/api/cars/" + id);
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), Car.class);
            } else if (response.statusCode() == 404) {
                return null;
            } else {
                throw new RestClientException("Error finding car: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RestClientException("Error finding car: " + e.getMessage());
        }
    }

    public Car create(Car car) throws RestClientException {
        try {
            URI uri = URI.create(baseUrl + "/api/cars");
            String jsonBody = objectMapper.writeValueAsString(car);
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("Authorization", getBasicAuthHeader())
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 201) {
                return objectMapper.readValue(response.body(), Car.class);
            } else if (response.statusCode() == 401 || response.statusCode() == 403) {
                throw new RestClientException("Ошибка аутентификации: Неверные учетные данные");
            } else {
                throw new RestClientException("Error creating car: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RestClientException("Error creating car: " + e.getMessage());
        }
    }

    public Car update(int id, Car car) throws RestClientException {
        try {
            URI uri = URI.create(baseUrl + "/api/cars/" + id);
            String jsonBody = objectMapper.writeValueAsString(car);
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .header("Authorization", getBasicAuthHeader())
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), Car.class);
            } else if (response.statusCode() == 401 || response.statusCode() == 403) {
                throw new RestClientException("Ошибка аутентификации: Неверные учетные данные");
            } else {
                throw new RestClientException("Error updating car: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RestClientException("Error updating car: " + e.getMessage());
        }
    }

    public boolean delete(int id) throws RestClientException {
        try {
            URI uri = URI.create(baseUrl + "/api/cars/" + id);
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", getBasicAuthHeader())
                .DELETE()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 204) {
                return true;
            } else if (response.statusCode() == 404) {
                return false;
            } else if (response.statusCode() == 401 || response.statusCode() == 403) {
                throw new RestClientException("Ошибка аутентификации: Неверные учетные данные");
            } else {
                throw new RestClientException("Error deleting car: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RestClientException("Error deleting car: " + e.getMessage());
        }
    }

    private String extractErrorMessage(HttpClientErrorException e) {
        try {
            ErrorResponse error = objectMapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class);
            if (error.getMessage() != null && error.getDetails() != null) {
                return error.getMessage() + "." + error.getDetails();
            } else if (error.getMessage() != null) {
                return error.getMessage();
            } else if (error.getDetails() != null) {
                return error.getDetails();
            }
            return "Неизвестная ошибка";
        } catch (Exception ex) {
            if (e.getStatusCode().value() == 404) {
                return "Запись не найдена";
            }
            return e.getMessage();
        }
    }

    private static class ErrorResponse {
        private String message;
        private String details;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
} 