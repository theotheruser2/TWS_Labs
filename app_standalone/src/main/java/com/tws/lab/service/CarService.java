package com.tws.lab.service;

import com.tws.lab.model.entity.Car;
import com.tws.lab.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> searchCars(String query, int limit, int offset) {
        return carRepository.findCar(query, limit, offset);
    }

    public Optional<Car> findById(Integer id) {
        return Optional.ofNullable(carRepository.findById(id));
    }

    @Transactional
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Transactional
    public void delete(Integer id) {
        carRepository.deleteById(id);
    }
} 