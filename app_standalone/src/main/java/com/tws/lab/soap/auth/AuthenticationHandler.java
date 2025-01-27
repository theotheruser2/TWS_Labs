package com.tws.lab.soap.auth;

import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.namespace.QName;
import java.util.*;

public class AuthenticationHandler implements SOAPHandler<SOAPMessageContext> {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
        if (!outbound) {
            String operationName = getOperationName(context);
            if (requiresAuthentication(operationName)) {
                Map<String, List<String>> headers = (Map<String, List<String>>) 
                    context.get(MessageContext.HTTP_REQUEST_HEADERS);
                
                if (headers == null || !headers.containsKey("Authorization")) {
                    throw new RuntimeException("Authentication required");
                }

                String authHeader = headers.get("Authorization").get(0);
                if (!authHeader.startsWith("Basic ")) {
                    throw new RuntimeException("Basic authentication required");
                }

                String base64Credentials = authHeader.substring("Basic ".length());
                String credentials = new String(Base64.getDecoder().decode(base64Credentials));
                String[] parts = credentials.split(":", 2);

                if (parts.length != 2 || !USERNAME.equals(parts[0]) || !PASSWORD.equals(parts[1])) {
                    throw new RuntimeException("Invalid credentials");
                }
            }
        }
        return true;
    }

    private boolean requiresAuthentication(String operationName) {
        if (operationName == null) {
            return false;
        }
        return operationName.contains("createCar") ||
               operationName.contains("updateCar") ||
               operationName.contains("deleteCarById");
    }

    private String getOperationName(SOAPMessageContext context) {
        try {
            SOAPMessage message = context.getMessage();
            if (message == null) {
                return null;
            }
            
            // Get operation name from SOAP Action header
            Map<String, List<String>> headers = (Map<String, List<String>>) 
                context.get(MessageContext.HTTP_REQUEST_HEADERS);
            if (headers != null && headers.containsKey("SOAPAction")) {
                String soapAction = headers.get("SOAPAction").get(0);
                if (soapAction != null && !soapAction.isEmpty()) {
                    // Remove quotes if present
                    return soapAction.replaceAll("\"", "");
                }
            }
            
            // Fallback: try to get from the SOAP body element name
            return message.getSOAPBody()
                         .getFirstChild()
                         .getLocalName();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }
} 