package com.tws.lab.service;

import com.tws.lab.config.ClientConfig;
import com.tws.lab.soap.*;
import jakarta.xml.ws.BindingProvider;
import java.net.URL;
import java.util.List;

public class CarClientService {
    private final CarWebService port;

    public CarClientService(String serviceUrl) throws Exception {
        CarService service = new CarService(new URL(serviceUrl));
        port = service.getCarWebServicePort();
        
        // Configure endpoint and authentication
        ((BindingProvider) port).getRequestContext()
                .put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceUrl);
        ClientConfig.configureAuthentication(port);
    }

    public List<Car> searchCars(CarListRequestDto requestDto) throws CarCrudException_Exception {
        return port.searchCars(requestDto);
    }

    public Car findCarById(int id) throws CarCrudException_Exception {
        return port.findCarById(id);
    }

    public int createCar(CarDto carDto) throws CarCrudException_Exception {
        return port.createCar(carDto);
    }

    public boolean updateCar(int id, CarDto carDto) throws CarCrudException_Exception {
        return port.updateCar(id, carDto);
    }

    public boolean deleteCarById(int id) throws CarCrudException_Exception {
        return port.deleteCarById(id);
    }

    public boolean uploadCarImage(int id, String imageBase64) throws CarCrudException_Exception {
        return port.uploadCarImage(id, imageBase64);
    }

    public String downloadCarImage(int id) throws CarCrudException_Exception {
        return port.downloadCarImage(id);
    }
} 