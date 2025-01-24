package com.tws.lab.service;

import com.tws.lab.annotation.Throttled;
import com.tws.lab.model.entity.Car;
import com.tws.lab.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    private void simulateProcessing() {
        try {
            // a small delay to simulate processing
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Throttled
    public List<Car> searchCars(String query, int limit, int offset) {
        simulateProcessing();
        return carRepository.findCar(query, limit, offset);
    }

    @Throttled
    public Optional<Car> findById(Integer id) {
        simulateProcessing();
        return Optional.ofNullable(carRepository.findById(id));
    }

    @Throttled
    @Transactional
    public Car save(Car car) {
        simulateProcessing();
        return carRepository.save(car);
    }

    @Throttled
    @Transactional
    public void delete(Integer id) {
        simulateProcessing();
        carRepository.deleteById(id);
    }
} 