package com.tws.lab.mapper;
import com.tws.lab.model.dto.CarDto;
import com.tws.lab.model.entity.Car;
public class CarMapper {
    public static CarDto toDto(Car car) {
        if (car == null) {
            return null;
        }
        return CarDto.builder()
                .brand(car.getBrand())
                .model(car.getModel())
                .release_year(car.getRelease_year())
                .license_plate(car.getLicense_plate())
                .owner_phone(car.getOwner_phone())
                .build();
    }
    public static Car toEntity(CarDto carDto) {
        if (carDto == null) {
            return null;
        }
        return Car.builder()
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .release_year(carDto.getRelease_year())
                .license_plate(carDto.getLicense_plate())
                .owner_phone(carDto.getOwner_phone())
                .build();
    }
}