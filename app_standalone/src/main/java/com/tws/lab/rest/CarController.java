package com.tws.lab.rest;

import com.tws.lab.model.dto.CarListRequestDto;
import com.tws.lab.model.entity.Car;
import com.tws.lab.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Car>> searchCars(
            @RequestParam(required = false) String query,
            @RequestParam(required = false, defaultValue = "10") Integer limit,
            @RequestParam(required = false, defaultValue = "0") Integer offset) {
        
        String decodedQuery = query != null ? URLDecoder.decode(query, StandardCharsets.UTF_8) : null;
        
        CarListRequestDto requestDto = CarListRequestDto.builder()
                .query(decodedQuery)
                .limit(limit)
                .offset(offset)
                .build();

        List<Car> cars = carService.searchCars(requestDto.getQuery(), limit, offset);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> findById(@PathVariable Integer id) {
        return carService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Car> create(@RequestBody Car car) {
        Car savedCar = carService.save(car);
        return ResponseEntity.ok(savedCar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> update(@PathVariable Integer id, @RequestBody Car car) {
        return carService.findById(id)
                .map(existingCar -> {
                    car.setId(id);
                    Car updatedCar = carService.save(car);
                    return ResponseEntity.ok(updatedCar);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return carService.findById(id)
                .map(car -> {
                    carService.delete(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 