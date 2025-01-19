package com.tws.lab.command;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tws.lab.soap.Car;
import com.tws.lab.soap.CarWebService;
import java.util.Scanner;
public class FindCarByIdCommand implements CliCommand {
    private final CarWebService carWebService;
    private final ObjectMapper objectMapper;
    public FindCarByIdCommand(CarWebService carWebService, ObjectMapper objectMapper) {
        this.carWebService = carWebService;
        this.objectMapper = objectMapper;
    }
    @Override
    public void execute(Scanner scanner) {
        int id = -1;
        while (true) {
            System.out.print("Введите идентификатор автомобиля (или введите 'exit' для выхода): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Выход из команды поиска автомобиля.");
                return;
            }

            try {
                id = Integer.parseInt(input);
                if (id <= 0) {
                    System.out.println("Ошибка: идентификатор должен быть положительным числом. Попробуйте снова.");
                    continue;
                }
                break; // Exit loop if valid
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите корректное число или 'exit' для выхода.");
            }
        }

        try {
            Car car = carWebService.findCarById(id);
            if (car == null) {
                System.out.println("Автомобиль с ID №" + id + " не найден.");
                return;
            }

            System.out.println("\nНайден автомобиль:");
            System.out.println(String.format("%-10s | %-10s | %-8s | %-15s | %-15s | %-5s",
                    "ID", "Бренд", "Модель", "Номер", "Телефон владельца", "Год выпуска"));
            System.out.println("-".repeat(85));
            System.out.println(String.format("%-10d | %-10s | %-8s | %-15s | %-17s | %-5d",
                    car.getId(), car.getBrand(), car.getModel(), car.getLicensePlate(),
                    car.getOwnerPhone(), car.getReleaseYear()));
        } catch (Exception e) {
            System.out.println("Ошибка при поиске автомобиля: " + e.getMessage());
        }
    }


    @Override
    public String getName() {
        return "findById";
    }
    @Override
    public String getDescription() {
        return "Поиск автомобиля по ID.";
    }
}