package com.tws.lab.command;

import com.tws.lab.model.Car;
import com.tws.lab.rest.CarRestClient;
import com.tws.lab.util.Util;

import java.util.Scanner;

public class FindByIdCommand implements Command {
    private final CarRestClient carRestClient;

    public FindByIdCommand(CarRestClient carRestClient) {
        this.carRestClient = carRestClient;
    }

    @Override
    public String getName() {
        return "findById";
    }

    @Override
    public String getDescription() {
        return "Поиск автомобиля по ID";
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Введите идентификатор автомобиля: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Car car = carRestClient.findById(id);
        if (car != null) {
            System.out.println("\nНайден автомобиль:");
            Util.printCarHeader();
            Util.printCar(car);
        } else {
            System.out.println("Автомобиль с ID №" + id + " не найден.");
        }
    }
} 