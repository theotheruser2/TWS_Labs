package com.tws.lab;

import com.tws.lab.command.*;
import com.tws.lab.service.JuddiService;
import java.util.*;

public class CliSoapClientApp {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Использование: java -jar client.jar <juddi-url> <service-url>");
            System.err.println("Пример: java -jar client.jar http://localhost:8080 http://localhost:8080/CarService?wsdl");
            System.exit(1);
        }

        String juddiUrl = args[0];
        String serviceUrl = args[1];

        try {
            JuddiService juddiService = new JuddiService(juddiUrl);
            Map<String, CliCommand> commands = new HashMap<>();

            // Add jUDDI commands
            commands.put("register", new RegisterServiceCommand(juddiService, serviceUrl));
            commands.put("find", new FindServiceCommand(juddiService));

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\nДоступные команды:");
                System.out.println("1) help - показать список команд");
                System.out.println("2) exit - выйти");
                System.out.println("3) find - Поиск сервиса в реестре jUDDI");
                System.out.println("4) register - Регистрация сервиса в реестре jUDDI");

                System.out.print("\nВведите команду: ");
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("exit")) {
                    break;
                }

                if (input.equals("help")) {
                    continue;
                }

                CliCommand command = commands.get(input);
                if (command != null) {
                    command.execute(scanner);
                } else {
                    System.out.println("Неизвестная команда. Используйте 'help' для списка команд.");
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        }
    }
}
