package com.tws.lab.command;

import com.tws.lab.model.Car;
import com.tws.lab.rest.CarRestClient;
import com.tws.lab.rest.error.RestClientException;
import com.tws.lab.util.InputValidator;
import com.tws.lab.util.InputValidator.ExitCommandException;
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
        try {
            int id = InputValidator.readIntegerOrExit(scanner, 
                "Введите идентификатор автомобиля для обновления записи (или введите 'exit' для выхода): ");

            Car existingCar = carRestClient.findById(id);
            if (existingCar != null) {
                System.out.println("Автомобиль найден. Введите новые данные для обновления записи.");
                System.out.println("Введите данные об автомобиле.");
                Car updatedCar = Util.readCarFromConsole(scanner);
                updatedCar.setId(id);
                Car result = carRestClient.update(id, updatedCar);
                if (result != null) {
                    System.out.println("Запись успешно обновлена.");
                }
            } else {
                System.out.println("Автомобиль с ID №" + id + " не найден.");
            }
        } catch (ExitCommandException e) {
            System.out.println("Выход из команды обновления записи об автомобиле.");
        } catch (RestClientException e) {
            System.err.println(e.getMessage());
        }
    }
} 