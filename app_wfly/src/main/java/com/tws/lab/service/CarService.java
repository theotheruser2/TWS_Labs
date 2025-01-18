package com.tws.lab.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.tws.lab.model.entity.Car;
import com.tws.lab.repository.CarRepository;

import java.util.List;

@ApplicationScoped
public class CarService {

    @Inject
    CarRepository carRepository;

    public CarService() {

    }

    public List<Car> searchCars(String query, int limit, int offset) {
        return carRepository.findCar(query, limit, offset);
    }
}
