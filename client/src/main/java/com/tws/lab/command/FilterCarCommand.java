package com.tws.lab.command;

import com.tws.lab.model.Car;
import com.tws.lab.rest.CarRestClient;

import java.util.List;
import java.util.Scanner;

public class FilterCarCommand implements Command {
    private final CarRestClient carRestClient;

    public FilterCarCommand(CarRestClient carRestClient) {
        this.carRestClient = carRestClient;
    }

    @Override
    public String getName() {
        return "search";
    }

    @Override
    public String getDescription() {
        return "Фильтрация автомобилей на основе запроса с дополнительными параметрами лимита и смещения";
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.println("\nДоступные поля и их типы в таблице:");
        System.out.println("- brand: String");
        System.out.println("- id: int");
        System.out.println("- licensePlate: String");
        System.out.println("- model: String");
        System.out.println("- ownerPhone: String");
        System.out.println("- releaseYear: Integer");
        
        System.out.println("\nИспользуйте операторы AND, OR для объединения нескольких условий, '(', ')' для группировки, а также операторы сравнения: =, >, <, >=, <=, ~");
        
        System.out.println("\nВведите запрос для фильтрации в формате 'имя_поля оператор значение'");
        System.out.print("(например, 'release_year=2018 AND brand=Ford'): ");
        String query = scanner.nextLine().trim();
        
        System.out.print("Введите лимит (по умолчанию 5): ");
        String limitStr = scanner.nextLine().trim();
        Integer limit = limitStr.isEmpty() ? 5 : Integer.parseInt(limitStr);
        
        System.out.print("Введите смещение (по умолчанию 0): ");
        String offsetStr = scanner.nextLine().trim();
        Integer offset = offsetStr.isEmpty() ? 0 : Integer.parseInt(offsetStr);

        List<Car> cars = carRestClient.searchCars(query, limit, offset);
        
        if (cars.isEmpty()) {
            System.out.println("Автомобили не найдены.");
            return;
        }

        // Print header
        System.out.println("\nНайденные автомобили:");
        System.out.println("ID         | Бренд      | Модель   | Номер           | Телефон владельца | Год выпуска");
        System.out.println("-------------------------------------------------------------------------------------");
        
        // Print each car
        for (Car car : cars) {
            System.out.printf("%-10d | %-10s | %-8s | %-14s | %-16s | %d%n",
                car.getId(),
                truncate(car.getBrand(), 10),
                truncate(car.getModel(), 8),
                truncate(car.getLicensePlate() != null ? car.getLicensePlate() : "Н/Д", 14),
                truncate(car.getOwnerPhone() != null ? car.getOwnerPhone() : "Н/Д", 16),
                car.getReleaseYear());
        }
    }

    private String truncate(String str, int maxLength) {
        if (str == null) return "";
        return str.length() <= maxLength ? str : str.substring(0, maxLength - 3) + "...";
    }
}