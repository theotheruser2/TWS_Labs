package com.tws.lab.soap.config;

import com.tws.lab.soap.auth.AuthenticationHandler;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.soap.SOAPBinding;
import java.util.List;

public class WebServiceConfig {
    public static void configureHandlers(Endpoint endpoint) {
        SOAPBinding binding = (SOAPBinding) endpoint.getBinding();
        List<Handler> handlerChain = binding.getHandlerChain();
        handlerChain.add(new AuthenticationHandler());
        binding.setHandlerChain(handlerChain);
    }
} 