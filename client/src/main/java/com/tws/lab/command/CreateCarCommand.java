package com.tws.lab.command;

import com.tws.lab.model.Car;
import com.tws.lab.rest.CarRestClient;
import com.tws.lab.rest.error.RestClientException;
import com.tws.lab.util.Util;

import java.util.Scanner;

public class CreateCarCommand implements Command {
    private final CarRestClient carRestClient;

    public CreateCarCommand(CarRestClient carRestClient) {
        this.carRestClient = carRestClient;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Создание новой записи об автомобиле";
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.println("Введите данные об автомобиле.");
            Car car = Util.readCarFromConsole(scanner);
            Car createdCar = carRestClient.create(car);
            if (createdCar != null) {
                System.out.println("Создание записи об автомобиле с ID: " + createdCar.getId());
            }
        } catch (RestClientException e) {
            System.err.println(e.getMessage());
        }
    }
} 