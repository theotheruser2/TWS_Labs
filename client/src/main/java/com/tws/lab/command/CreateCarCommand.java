package com.tws.lab.command;
import com.tws.lab.soap.CarDto;
import com.tws.lab.service.CarClientService;
import com.tws.lab.utils.Util;
import com.tws.lab.soap.CarCrudException_Exception;
import java.util.Scanner;
public class CreateCarCommand implements CliCommand {
    private final CarClientService carClientService;
    public CreateCarCommand(CarClientService carClientService) {
        this.carClientService = carClientService;
    }
    @Override
    public void execute(Scanner scanner){
        CarDto carDto = Util.getCarDtoFromInput(scanner);
        try {
            int id = carClientService.createCar(carDto);
            System.out.println("Создание записи об автомобиле с ID: " + id);
        } catch (CarCrudException_Exception e) {
            System.out.println("Ошибка при создании записи об автомобиле: " + e.getFaultInfo().getErrorInfo().getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при создании объекта: " + e.getMessage());
        }
    }
    @Override
    public String getName() {
        return "create";
    }
    @Override
    public String getDescription() {
        return "Создание новой записи об автомобиле.";
    }
}