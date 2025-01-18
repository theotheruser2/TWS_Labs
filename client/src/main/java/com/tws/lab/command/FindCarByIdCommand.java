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
        System.out.print("Введите идентификатор автомобиля: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // очистка буфера
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