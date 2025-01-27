package com.tws.lab.command;

import com.tws.lab.service.CarClientService;
import com.tws.lab.soap.CarCrudException_Exception;

import java.util.Scanner;

public class DeleteCarCommand implements CliCommand {
    private final CarClientService carClientService;

    public DeleteCarCommand(CarClientService carClientService) {
        this.carClientService = carClientService;
    }

    @Override
    public void execute(Scanner scanner) {
        int id = -1;
        while (true) {
            System.out.print("Введите идентификатор автомобиля, чтобы удалить его (или введите 'exit' для выхода): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Выход из команды удаления записи об автомобиле.");
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
            boolean success = carClientService.deleteCarById(id);
            if (success) {
                System.out.println("Запись об автомобиле успешно удалена.");
            } else {
                System.out.println("Автомобиль с ID №" + id + " не найден.");
            }
        } catch (CarCrudException_Exception e) {
            System.out.println("Ошибка при удалении записи об автомобиле: " + e.getFaultInfo().getErrorInfo().getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при удалении записи об автомобиле: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Удаление записи об автомобиле по ID.";
    }
}