package com.tws.lab.service;

import com.tws.lab.model.entity.Car;
import com.tws.lab.repository.CarRepository;
import com.tws.lab.mapper.CarMapper;
import com.tws.lab.model.dto.CarDto;
import com.tws.lab.soap.errorHandling.CarCrudException;

import java.util.List;

public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> searchCars(String query, int limit, int offset) throws CarCrudException{
        return carRepository.findCar(query, limit, offset);
    }
    public Car readCar(int id) {
        return carRepository.readCar(id);
    }
    public int createCar(CarDto carDto) {
        Car car = CarMapper.toEntity(carDto);
        return carRepository.createCar(car);
    }
    public boolean updateCar(int id, CarDto carDto) {
        Car car = CarMapper.toEntity(carDto);
        return carRepository.updateCar(id, car);
    }
    public boolean deleteCarById(int id) {
        return carRepository.deleteCarById(id);
    }
}
