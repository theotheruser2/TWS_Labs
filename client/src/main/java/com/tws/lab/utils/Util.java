package com.tws.lab.utils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.tws.lab.soap.CarService;
import com.tws.lab.soap.CarWebService;
import com.tws.lab.command.*;
import com.tws.lab.soap.CarDto;

public class Util {
    public static int getIntInput(Scanner scanner, String prompt, int defaultValue) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                return defaultValue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное числовое значение.");
            }
        }
    }
    public static CarDto getCarDtoFromInput(Scanner scanner) {
        System.out.println("Введите данные об автомобиле:");
        System.out.print("Марка: ");
        String brand = scanner.nextLine();
        System.out.print("Модель: ");
        String model = scanner.nextLine();
        System.out.print("Год выпуска: ");
        int release_year = Integer.parseInt(scanner.nextLine());
        System.out.print("Регистрационный номер: ");
        String license_plate = scanner.nextLine();
        System.out.print("Телефон владельца: ");
        String owner_phone = scanner.nextLine();
        CarDto carDto = new CarDto();
        carDto.setBrand(brand);
        carDto.setModel(model);
        carDto.setReleaseYear(release_year);
        carDto.setLicensePlate(license_plate);
        carDto.setOwnerPhone(owner_phone);
        return carDto;
    }
    public static Map<String, CliCommand> produceCommands(String soapUrl) throws Exception {
        Map<String, CliCommand> commands = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        objectMapper.setDefaultPrettyPrinter(prettyPrinter);

        URL url = new URL(soapUrl);
        CarService carService = new CarService(url);
        CarWebService carWebServiceProxy = carService.getCarWebServicePort();

        FilterCarCommand filterCarCommand = new FilterCarCommand(carWebServiceProxy, objectMapper);
        commands.put(filterCarCommand.getName(), filterCarCommand);

        FindCarByIdCommand findCarByIdCommand = new FindCarByIdCommand(carWebServiceProxy, objectMapper);
        commands.put(findCarByIdCommand.getName(), findCarByIdCommand);

        CreateCarCommand createCarCommand = new CreateCarCommand(carWebServiceProxy);
        commands.put(createCarCommand.getName(), createCarCommand);

        UpdateCarCommand updateCarCommand = new UpdateCarCommand(carWebServiceProxy);
        commands.put(updateCarCommand.getName(), updateCarCommand);

        DeleteCarCommand deleteCarCommand = new DeleteCarCommand(carWebServiceProxy);
        commands.put(deleteCarCommand.getName(), deleteCarCommand);

        HelpCommand helpCommand = new HelpCommand(commands);
        commands.put(helpCommand.getName(), helpCommand);

        ExitCommand exitCommand = new ExitCommand();
        commands.put(exitCommand.getName(), exitCommand);

        return commands;
    }
}
