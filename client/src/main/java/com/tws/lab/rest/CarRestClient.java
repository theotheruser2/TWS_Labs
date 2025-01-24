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

public class CarRestClient {
    private final String baseUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public CarRestClient(String baseUrl) {
        // Remove trailing slash if present
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public List<Car> searchCars(String query, int limit, int offset) {
        try {
            // Split query into parts, encode only the values
            String encodedQuery = null;
            if (query != null && !query.trim().isEmpty()) {
                String[] parts = query.split("\\s+");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < parts.length; i++) {
                    if (i > 0) {
                        sb.append(" ");
                    }
                    // Don't encode operators or field names
                    if (parts[i].equals("=") || parts[i].equals(">") || parts[i].equals("<") || 
                        parts[i].equals(">=") || parts[i].equals("<=") || parts[i].equals("~") ||
                        parts[i].equals("AND") || parts[i].equals("OR")) {
                        sb.append(parts[i]);
                    } else if (i % 3 == 2) {
                        sb.append(URLEncoder.encode(parts[i], StandardCharsets.UTF_8));
                    } else {
                        sb.append(parts[i]);
                    }
                }
                encodedQuery = sb.toString();
            }

            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/api/cars/search")
                    .queryParam("limit", limit)
                    .queryParam("offset", offset)
                    .queryParam("query", encodedQuery)
                    .build(false) // Don't encode the full URL
                    .toUriString();
            
            System.out.println("Requesting URL: " + url);
            Car[] response = restTemplate.getForObject(url, Car[].class);
            return response != null ? Arrays.asList(response) : Collections.emptyList();
        } catch (HttpClientErrorException e) {
            String errorMessage = extractErrorMessage(e);
            throw new RestClientException("Ошибка при поиске записей об автомобиле: " + errorMessage);
        } catch (Exception e) {
            throw new RestClientException("Ошибка при поиске записей об автомобиле: " + e.getMessage());
        }
    }

    public Car findById(Integer id) {
        try {
            ResponseEntity<Car> response = restTemplate.getForEntity(
                    baseUrl + "/api/cars/" + id,
                    Car.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return null;
            }
            String errorMessage = extractErrorMessage(e);
            throw new RestClientException("Ошибка при поиске автомобиля: " + errorMessage);
        } catch (Exception e) {
            throw new RestClientException("Ошибка при поиске автомобиля: " + e.getMessage());
        }
    }

    public Car create(Car car) {
        try {
            ResponseEntity<Car> response = restTemplate.postForEntity(
                    baseUrl + "/api/cars",
                    car,
                    Car.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            String errorMessage = extractErrorMessage(e);
            throw new RestClientException("Ошибка при создании записи об автомобиле: " + errorMessage);
        } catch (Exception e) {
            throw new RestClientException("Ошибка при создании записи об автомобиле: " + e.getMessage());
        }
    }

    public Car update(Integer id, Car car) {
        try {
            ResponseEntity<Car> response = restTemplate.exchange(
                    baseUrl + "/api/cars/" + id,
                    HttpMethod.PUT,
                    new org.springframework.http.HttpEntity<>(car),
                    Car.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            String errorMessage = extractErrorMessage(e);
            throw new RestClientException("Ошибка при обновлении записи об автомобиле: " + errorMessage);
        } catch (Exception e) {
            throw new RestClientException("Ошибка при обновлении записи об автомобиле: " + e.getMessage());
        }
    }

    public boolean delete(Integer id) {
        try {
            restTemplate.delete(baseUrl + "/api/cars/" + id);
            return true;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return false;
            }
            String errorMessage = extractErrorMessage(e);
            throw new RestClientException("Ошибка при удалении записи об автомобиле: " + errorMessage);
        } catch (Exception e) {
            throw new RestClientException("Ошибка при удалении записи об автомобиле: " + e.getMessage());
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