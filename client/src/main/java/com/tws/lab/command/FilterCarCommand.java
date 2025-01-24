package com.tws.lab.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tws.lab.soap.Car;
import com.tws.lab.soap.CarListRequestDto;
import com.tws.lab.soap.CarWebService;
import com.tws.lab.utils.Util;
import com.tws.lab.soap.CarCrudException_Exception;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

public class FilterCarCommand implements CliCommand {
    private final CarWebService carWebService;
    private final ObjectMapper objectMapper;

    public FilterCarCommand(CarWebService carWebService, ObjectMapper objectMapper) {
        this.carWebService = carWebService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.println("Доступные поля и их типы в таблице:");
            for (Field field : Car.class.getDeclaredFields()) {
                System.out.println("- " + field.getName() + ": " + field.getType().getSimpleName());
            }
            System.out.println("\nИспользуйте операторы AND, OR для объединения нескольких условий, '(', ')' для группировки, а также операторы сравнения: =, >, <, >=, <=\n");

            System.out.print("Введите запрос для фильтрации в формате 'имя_поля оператор значение'\n(например, 'release_year=2018 AND brand=Ford'): ");
            String query = scanner.nextLine();

            int limit = Util.getIntInput(scanner, "Введите лимит (по умолчанию 5): ", 5);
            int offset = Util.getIntInput(scanner, "Введите смещение (по умолчанию 0): ", 0);

            CarListRequestDto carListRequestDto = new CarListRequestDto();
            carListRequestDto.setLimit(limit);
            carListRequestDto.setOffset(offset);
            carListRequestDto.setQuery(query);

            List<Car> filteredCars = carWebService.searchCars(carListRequestDto);
            if (filteredCars.isEmpty()) {
                System.out.println("Нет автомобилей, соответствующих критериям фильтрации.");
            } else {
                System.out.println("\nНайденные автомобили:");
                System.out.println(String.format("%-10s | %-10s | %-8s | %-15s | %-15s | %-5s",
                        "ID", "Бренд", "Модель", "Номер", "Телефон владельца", "Год выпуска" ));
                String line = new String(new char[85]).replace('\0', '-');
                System.out.println(line);

                for (Car car : filteredCars) {
                    System.out.println(String.format("%-10d | %-10s | %-8s | %-15s | %-17s | %-5d",
                            car.getId(), car.getBrand(), car.getModel(), car.getLicensePlate(),
                            car.getOwnerPhone(), car.getReleaseYear()));
                }
            }
        } catch (CarCrudException_Exception e) {
            System.out.println("Ошибка при поиске записей об автомобиле: " + e.getFaultInfo().getErrorInfo().getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка фильтрации: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "search";
    }

    @Override
    public String getDescription() {
        return "Фильтрация автомобилей на основе запроса с дополнительными параметрами лимита и смещения";
    }
}