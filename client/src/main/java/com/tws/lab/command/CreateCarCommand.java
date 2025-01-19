package com.tws.lab.command;
import com.tws.lab.soap.CarDto;
import com.tws.lab.soap.CarWebService;
import com.tws.lab.utils.Util;
import com.tws.lab.soap.CarCrudException_Exception;
import java.util.Scanner;
public class CreateCarCommand implements CliCommand {
    private final CarWebService carWebService;
    public CreateCarCommand(CarWebService carWebService) {
        this.carWebService = carWebService;
    }
    @Override
    public void execute(Scanner scanner){
        CarDto carDto = Util.getCarDtoFromInput(scanner);
        try {
            int id = carWebService.createCar(carDto);
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