package com.tws.lab.command;

import com.tws.lab.rest.CarRestClient;
import com.tws.lab.rest.error.RestClientException;

import java.util.Scanner;

public class DeleteCarCommand implements Command {
    private final CarRestClient carRestClient;

    public DeleteCarCommand(CarRestClient carRestClient) {
        this.carRestClient = carRestClient;
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Удаление записи об автомобиле по ID";
    }

    @Override
    public void execute(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите идентификатор автомобиля, чтобы удалить его (или введите 'exit' для выхода): ");
                String input = scanner.nextLine().trim();
                
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Выход из команды удаления записи об автомобиле.");
                    return;
                }
                
                try {
                    int id = Integer.parseInt(input);
                    if (carRestClient.delete(id)) {
                        System.out.println("Запись об автомобиле успешно удалена.");
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