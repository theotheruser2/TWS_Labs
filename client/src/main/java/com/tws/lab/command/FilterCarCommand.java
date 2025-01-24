package com.tws.lab.command;

import com.tws.lab.model.Car;
import com.tws.lab.rest.CarRestClient;
import com.tws.lab.rest.error.RestClientException;
import com.tws.lab.util.Util;

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
        try {
            System.out.println("Доступные поля и их типы в таблице:");
            System.out.println("- brand: String");
            System.out.println("- id: int");
            System.out.println("- licensePlate: String");
            System.out.println("- model: String");
            System.out.println("- ownerPhone: String");
            System.out.println("- releaseYear: Integer");
            System.out.println();
            System.out.println("Используйте операторы AND, OR для объединения нескольких условий, '(', ')' для группировки, а также операторы сравнения: =, >, <, >=, <=");
            System.out.println();
            System.out.println("Введите запрос для фильтрации в формате 'имя_поля оператор значение'");
            System.out.print("(например, 'release_year=2018 AND brand=Ford'): ");
            String query = scanner.nextLine().trim();

            System.out.print("Введите лимит (по умолчанию 5): ");
            String limitStr = scanner.nextLine().trim();
            int limit = limitStr.isEmpty() ? 5 : Integer.parseInt(limitStr);

            System.out.print("Введите смещение (по умолчанию 0): ");
            String offsetStr = scanner.nextLine().trim();
            int offset = offsetStr.isEmpty() ? 0 : Integer.parseInt(offsetStr);

            List<Car> cars = carRestClient.searchCars(query, limit, offset);
            if (cars.isEmpty()) {
                System.out.println("Нет автомобилей, соответствующих критериям фильтрации.");
            } else {
                Util.printCarHeader();
                cars.forEach(Util::printCar);
            }
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: Введите корректное число для лимита и смещения.");
        } catch (RestClientException e) {
            System.err.println(e.getMessage());
        }
    }
}