package com.tws.lab.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import com.tws.lab.model.dto.CarListRequestDto;
import com.tws.lab.model.entity.Car;
import com.tws.lab.service.CarService;
import com.tws.lab.model.dto.CarDto;
import com.tws.lab.soap.errorHandling.*;
import com.tws.lab.service.ValidationService;

import java.util.Collections;
import java.util.List;

@WebService(serviceName = "CarService")
public class CarWebService {
    private final CarService carService;

    public CarWebService(CarService carService) {
        this.carService = carService;
    }

    @WebMethod
    public List<Car> searchCars(@WebParam(name = "arg0") CarListRequestDto carListRequestDto) throws CarCrudException{
        if (carListRequestDto == null) {
            System.out.println(" null CarListRequestDto");
            return Collections.emptyList();
        }

        int limit = carListRequestDto.getLimit() != null ? carListRequestDto.getLimit() : 10;
        int offset = carListRequestDto.getOffset() != null ? carListRequestDto.getOffset() : 0;

        return carService.searchCars(carListRequestDto.getQuery(), limit, offset);
    }
    @WebMethod
    public Car findCarById(@WebParam(name = "id") int id) {
        return carService.readCar(id);
    }
    @WebMethod
    public int createCar(@WebParam(name = "carDto") CarDto carDto) throws CarCrudException{
        ValidationService.validateCarDto(carDto);
        return carService.createCar(carDto);
    }
    @WebMethod
    public boolean updateCar(@WebParam(name = "id") int id, @WebParam(name = "carDto") CarDto carDto) throws CarCrudException {
        ValidationService.validateCarDto(carDto);
        boolean updated = carService.updateCar(id, carDto);
        if (!updated) {
            throw new CarCrudException("Автомобиль не найден", new ErrorBean("Не найдена запись с идентификатором: " + id));
        }
        return true;    }
    @WebMethod
    public boolean deleteCarById(@WebParam(name = "id") int id) {
        return carService.deleteCarById(id);
    }
}
