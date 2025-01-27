package com.tws.lab.command;

import com.tws.lab.service.CarClientService;
import com.tws.lab.soap.Car;
import com.tws.lab.soap.CarDto;
import com.tws.lab.utils.Util;
import com.tws.lab.soap.CarCrudException_Exception;

import java.util.Scanner;

public class UpdateCarCommand implements CliCommand {
    private final CarClientService carClientService;

    public UpdateCarCommand(CarClientService carClientService) {
        this.carClientService = carClientService;
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.print("Введите идентификатор автомобиля для обновления записи: ");
        int id = Integer.parseInt(scanner.nextLine());
        try {
            Car existingCar = carClientService.findCarById(id);
            if (existingCar == null) {
                System.out.println("Автомобиль с ID №" + id + " не найден.");
                return;
            }
            System.out.println("Автомобиль найден. Введите новые данные для обновления записи.");
            CarDto updatedCarDto = Util.getCarDtoFromInput(scanner);
            boolean success = carClientService.updateCar(id, updatedCarDto);
            if (success) {
                System.out.println("Запись успешно обновлена.");
            } else {
                System.out.println("Не удалось обновить запись.");
            }
        } catch (CarCrudException_Exception e) {
            System.out.println("Ошибка при обновлении записи об автомобиле: " + e.getFaultInfo().getErrorInfo().getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении записи: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Обновление записи об автомобиле по ID.";
    }
}