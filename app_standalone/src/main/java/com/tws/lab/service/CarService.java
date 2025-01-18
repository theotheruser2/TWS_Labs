package com.tws.lab.service;

import com.tws.lab.model.entity.Car;
import com.tws.lab.repository.CarRepository;

import java.util.List;

public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> searchCars(String query, int limit, int offset) {
        return carRepository.findCar(query, limit, offset);
    }
}
