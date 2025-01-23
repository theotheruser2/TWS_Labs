package com.tws.lab.command;

import com.tws.lab.model.Car;
import com.tws.lab.rest.CarRestClient;
import com.tws.lab.util.Util;

import java.util.Scanner;

public class UpdateCarCommand implements Command {
    private final CarRestClient carRestClient;

    public UpdateCarCommand(CarRestClient carRestClient) {
        this.carRestClient = carRestClient;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Обновление записи об автомобиле по ID";
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Введите идентификатор автомобиля для обновления записи: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Car existingCar = carRestClient.findById(id);
        if (existingCar != null) {
            System.out.println("Автомобиль найден. Введите новые данные для обновления записи.");
            System.out.println("Введите данные об автомобиле:");
            Car updatedCar = Util.readCarFromConsole(scanner);
            updatedCar.setId(id);
            Car result = carRestClient.update(id, updatedCar);
            if (result != null) {
                System.out.println("Запись успешно обновлена.");
            } else {
                System.out.println("Ошибка при обновлении записи.");
            }
        } else {
            System.out.println("Автомобиль с ID №" + id + " не найден.");
        }
    }
} 