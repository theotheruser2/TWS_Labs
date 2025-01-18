package com.tws.lab.command;
import com.tws.lab.soap.CarWebService;
import java.util.Scanner;
public class DeleteCarCommand implements CliCommand {
    private final CarWebService carWebService;
    public DeleteCarCommand(CarWebService carWebService) {
        this.carWebService = carWebService;
    }
    @Override
    public void execute(Scanner scanner) {
        System.out.print("Введите идентификатор автомобиля, чтобы удалить его: ");
        int id = Integer.parseInt(scanner.nextLine());
        try {
            boolean success = carWebService.deleteCarById(id);
            if (success) {
                System.out.println("Запись об автомобиле успешно удалена.");
            } else {
                System.out.println("Автомобиль с ID №" + id + " не найден.");
            }
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