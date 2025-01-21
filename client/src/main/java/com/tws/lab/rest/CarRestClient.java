package com.tws.lab.rest;

import com.tws.lab.model.Car;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
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

    public CarRestClient(String baseUrl) {
        // Remove trailing slash if present
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
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
        } catch (RestClientException e) {
            System.err.println("Error searching cars: " + e.getMessage());
            return Collections.emptyList();
        }
    }
} 