package com.tws.lab.utils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;

import com.tws.lab.service.CarClientService;
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

        CarDto carDto = new CarDto();
        System.out.println("Введите данные об автомобиле.");
        for (Field field : CarDto.class.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String fieldType = field.getType().getSimpleName();
            String userFriendlyName = switch (fieldName) {
                case "brand" -> "Марка";
                case "model" -> "Модель";
                case "releaseYear" -> "Год выпуска";
                case "licensePlate" -> "Регистрационный номер";
                case "ownerPhone" -> "Телефон владельца";
                default -> fieldName;
            };

            while (true) {
                System.out.print(userFriendlyName + " (" + fieldType + "): ");
                String input = scanner.nextLine().trim();

                try {
                    if (fieldType.equals("String")) {
                        field.set(carDto, input);
                    } else if (fieldType.equals("Integer")) {
                        try {
                            int value = Integer.parseInt(input);
                            if (fieldName.equals("releaseYear") && value < 1885) {
                                throw new IllegalArgumentException("Год выпуска не может быть ранее 1885.");
                            }
                            field.set(carDto, value);
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка: Введите корректное число.");
                            continue;
                        }
                    }
                    break; // Exit the loop if input is valid
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    System.out.println("Ошибка ввода: " + e.getMessage());
                }
            }
        }
        return carDto;
    }

    public static Map<String, CliCommand> produceCommands(String soapUrl) throws Exception {
        Map<String, CliCommand> commands = new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        objectMapper.setDefaultPrettyPrinter(prettyPrinter);

        CarClientService carClientService = new CarClientService(soapUrl);

        FilterCarCommand filterCarCommand = new FilterCarCommand(carClientService, objectMapper);
        commands.put(filterCarCommand.getName(), filterCarCommand);

        FindCarByIdCommand findCarByIdCommand = new FindCarByIdCommand(carClientService, objectMapper);
        commands.put(findCarByIdCommand.getName(), findCarByIdCommand);

        CreateCarCommand createCarCommand = new CreateCarCommand(carClientService);
        commands.put(createCarCommand.getName(), createCarCommand);

        UpdateCarCommand updateCarCommand = new UpdateCarCommand(carClientService);
        commands.put(updateCarCommand.getName(), updateCarCommand);

        DeleteCarCommand deleteCarCommand = new DeleteCarCommand(carClientService);
        commands.put(deleteCarCommand.getName(), deleteCarCommand);

        UploadImageCommand uploadImageCommand = new UploadImageCommand(carClientService);
        commands.put(uploadImageCommand.getName(), uploadImageCommand);

        DownloadImageCommand downloadImageCommand = new DownloadImageCommand(carClientService);
        commands.put(downloadImageCommand.getName(), downloadImageCommand);

        HelpCommand helpCommand = new HelpCommand(commands);
        commands.put(helpCommand.getName(), helpCommand);

        ExitCommand exitCommand = new ExitCommand();
        commands.put(exitCommand.getName(), exitCommand);

        return commands;
    }
}
