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
                .release_year(car.getReleaseYear())
                .license_plate(car.getLicensePlate())
                .owner_phone(car.getOwnerPhone())
                .build();
    }
    public static Car toEntity(CarDto carDto) {
        if (carDto == null) {
            return null;
        }
        return Car.builder()
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .release_year(carDto.getReleaseYear())
                .license_plate(carDto.getLicensePlate())
                .owner_phone(carDto.getOwnerPhone())
                .build();
    }
}