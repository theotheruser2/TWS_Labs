package com.tws.lab.util;

import com.tws.lab.command.*;
import com.tws.lab.model.Car;
import com.tws.lab.rest.CarRestClient;

import java.util.HashMap;
import java.util.Map;

public class Util {
    public static Map<String, Command> produceRestCommands(CarRestClient carRestClient) {
        Map<String, Command> commands = new HashMap<>();
        commands.put("search", new FilterCarCommand(carRestClient));
        commands.put("findById", new FindByIdCommand(carRestClient));
        commands.put("create", new CreateCarCommand(carRestClient));
        commands.put("update", new UpdateCarCommand(carRestClient));
        commands.put("delete", new DeleteCarCommand(carRestClient));
        return commands;
    }

    public static void printCarHeader() {
        System.out.println("ID         | Бренд      | Модель   | Номер           | Телефон владельца | Год выпуска");
        System.out.println("-------------------------------------------------------------------------------------");
    }

    public static void printCar(Car car) {
        System.out.printf("%-10s | %-10s | %-8s | %-14s | %-16s | %d%n",
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getLicensePlate(),
                car.getOwnerPhone(),
                car.getReleaseYear());
    }

    public static Car readCarFromConsole(java.util.Scanner scanner) {
        System.out.print("Марка (String): ");
        String brand = scanner.nextLine().trim();

        System.out.print("Регистрационный номер (String): ");
        String licensePlate = scanner.nextLine().trim();

        System.out.print("Модель (String): ");
        String model = scanner.nextLine().trim();

        System.out.print("Телефон владельца (String): ");
        String ownerPhone = scanner.nextLine().trim();

        Integer releaseYear = null;
        while (releaseYear == null) {
            System.out.print("Год выпуска (Integer): ");
            String input = scanner.nextLine().trim();
            try {
                if (!input.isEmpty()) {
                    releaseYear = Integer.parseInt(input);
                    if (releaseYear < 1885) {
                        System.out.println("Ошибка ввода: Год выпуска не может быть ранее 1885.");
                        releaseYear = null;
                    }
                } else {
                    System.out.println("Ошибка: Введите корректное число.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите корректное число.");
            }
        }

        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setReleaseYear(releaseYear);
        car.setLicensePlate(licensePlate);
        car.setOwnerPhone(ownerPhone);
        return car;
    }
} 