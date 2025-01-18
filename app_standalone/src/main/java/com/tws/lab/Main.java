package com.tws.lab;

import com.tws.lab.repository.EntityManagerFactoryProvider;
import com.tws.lab.repository.CarRepository;
import com.tws.lab.service.CarService;
import com.tws.lab.soap.CarWebService;

import jakarta.xml.ws.Endpoint;

import java.util.Map;


public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");

        Map<String, String> env = System.getenv();
        String dbUrl = env.getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/postgres");
        String dbUser = env.getOrDefault("DB_USER", "postgres");
        String dbPassword = env.getOrDefault("DB_PASSWORD", "postgres");

        EntityManagerFactoryProvider entityManagerFactoryProvider = new EntityManagerFactoryProvider(
                dbUrl,
                dbUser,
                dbPassword
        );
        CarRepository carRepository = new CarRepository(entityManagerFactoryProvider.getEntityManagerFactory());
        CarService carService = new CarService(carRepository);

        String url = env.getOrDefault("SOAP_SERVICE_URL", "http://localhost:8080/CarService");
        Endpoint.publish(url, new CarWebService(carService));

        System.out.println("Сервис запущен.");
    }
}
