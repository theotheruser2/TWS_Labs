package com.tws.lab.command;

import com.tws.lab.model.Car;
import com.tws.lab.rest.CarRestClient;
import com.tws.lab.rest.error.RestClientException;
import com.tws.lab.util.InputValidator;
import com.tws.lab.util.InputValidator.ExitCommandException;
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
        while (true) {
            try {
                System.out.print("Введите идентификатор автомобиля (или введите 'exit' для выхода): ");
                String input = scanner.nextLine().trim();
                
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Выход из команды поиска автомобиля.");
                    return;
                }
                
                try {
                    int id = Integer.parseInt(input);
                    Car car = carRestClient.findById(id);
                    if (car != null) {
                        System.out.println("\nНайден автомобиль:");
                        Util.printCarHeader();
                        Util.printCar(car);
                    } else {
                        System.out.println("Автомобиль с ID №" + id + " не найден.");
                    }
                    return;
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: Введите корректное число или 'exit' для выхода.");
                }
            } catch (RestClientException e) {
                System.err.println(e.getMessage());
                return;
            }
        }
    }
} 