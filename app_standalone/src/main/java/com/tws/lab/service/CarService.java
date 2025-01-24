package com.tws.lab.service;

import com.tws.lab.model.entity.Car;
import com.tws.lab.repository.CarRepository;
import com.tws.lab.rest.error.CarValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CarService {
    private final CarRepository carRepository;
    private final Validator validator;

    public CarService(CarRepository carRepository, Validator validator) {
        this.carRepository = carRepository;
        this.validator = validator;
    }

    public List<Car> searchCars(String query, int limit, int offset) {
        return carRepository.findCar(query, limit, offset);
    }

    public Optional<Car> findById(Integer id) {
        return Optional.ofNullable(carRepository.findById(id));
    }

    @Transactional
    public Car save(Car car) {
        validateCar(car);
        return carRepository.save(car);
    }

    @Transactional
    public void delete(Integer id) {
        carRepository.deleteById(id);
    }

    private void validateCar(Car car) {
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(" "));
            throw new CarValidationException(errorMessage);
        }
    }
} 