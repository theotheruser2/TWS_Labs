package com.tws.lab.utils;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tws.lab.command.CliCommand;
import com.tws.lab.command.ExitCommand;
import com.tws.lab.command.FilterCarCommand;
import com.tws.lab.command.HelpCommand;
import com.tws.lab.soap.CarService;
import com.tws.lab.soap.CarWebService;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

        HelpCommand helpCommand = new HelpCommand(commands);
        commands.put(helpCommand.getName(), helpCommand);

        ExitCommand exitCommand = new ExitCommand();
        commands.put(exitCommand.getName(), exitCommand);

        return commands;
    }
}
