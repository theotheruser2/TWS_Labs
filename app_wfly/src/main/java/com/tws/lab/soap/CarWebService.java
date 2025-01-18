package com.tws.lab.soap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import com.tws.lab.model.dto.CarListRequestDto;
import com.tws.lab.model.entity.Car;
import com.tws.lab.service.CarService;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
@WebService(serviceName = "CarService")
public class CarWebService {
    @Inject
    CarService carService;

    public CarWebService() {
    }

    @WebMethod
    public List<Car> searchCars(@WebParam(name = "arg0") CarListRequestDto carListRequestDto) {
        if (carListRequestDto == null) {
            System.out.println("null CarListRequestDto");
            return Collections.emptyList();
        }

        int limit = carListRequestDto.getLimit() != null ? carListRequestDto.getLimit() : 10;
        int offset = carListRequestDto.getOffset() != null ? carListRequestDto.getOffset() : 0;

        return carService.searchCars(carListRequestDto.getQuery(), limit, offset);
    }
}
