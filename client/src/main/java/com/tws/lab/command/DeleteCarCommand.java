package com.tws.lab.command;

import com.tws.lab.rest.CarRestClient;

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
        System.out.print("Введите идентификатор автомобиля, чтобы удалить его: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        if (carRestClient.delete(id)) {
            System.out.println("Запись об автомобиле успешно удалена.");
        } else {
            System.out.println("Автомобиль с ID №" + id + " не найден.");
        }
    }
} 