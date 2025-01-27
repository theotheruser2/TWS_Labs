package com.tws.lab.config;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ClientConfig {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    public static void configureAuthentication(Object port) {
        BindingProvider bindingProvider = (BindingProvider) port;
        Map<String, Object> requestContext = bindingProvider.getRequestContext();

        // Create authentication header
        String auth = USERNAME + ":" + PASSWORD;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        String authHeader = "Basic " + encodedAuth;

        // Set up headers
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Authorization", Collections.singletonList(authHeader));
        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
    }
} 