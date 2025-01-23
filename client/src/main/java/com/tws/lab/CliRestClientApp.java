package com.tws.lab;

import com.tws.lab.command.Command;
import com.tws.lab.command.FilterCarCommand;
import com.tws.lab.rest.CarRestClient;
import com.tws.lab.util.Util;

import java.util.Map;
import java.util.Scanner;

public class CliRestClientApp {
    public static void main(String[] args) {
        String baseUrl = args.length > 0 ? args[0] : "http://localhost:8080";
        CarRestClient carRestClient = new CarRestClient(baseUrl);
        Map<String, Command> commands = Util.produceRestCommands(carRestClient);
        commands.put("help", new Command() {
            @Override
            public String getName() {
                return "help";
            }

            @Override
            public String getDescription() {
                return "Показать список доступных команд";
            }

            @Override
            public void execute(Scanner scanner) {
                System.out.println("Команды:");
                System.out.println("1) help - Показать список доступных команд");
                System.out.println("2) exit - Выход из приложения");
                System.out.println("3) search - Фильтрация автомобилей на основе запроса с дополнительными параметрами лимита и смещения");
                System.out.println("4) findById - Поиск автомобиля по ID");
                System.out.println("5) create - Создание новой записи об автомобиле");
                System.out.println("6) update - Обновление записи об автомобиле по ID");
                System.out.println("7) delete - Удаление записи об автомобиле по ID");
            }
        });

        Scanner scanner = new Scanner(System.in);
        System.out.println("Для получения списка доступных команд используйте 'help'.");
        
        while (true) {
            System.out.print("\nВведите команду: ");
            String commandName = scanner.nextLine().trim();

            if (commandName.equals("exit")) {
                System.out.println("Выход из приложения");
                break;
            }

            Command command = commands.get(commandName);
            if (command == null) {
                System.out.println("Неизвестная команда. Для получения списка доступных команд используйте 'help'.");
                continue;
            }

            try {
                command.execute(scanner);
            } catch (Exception e) {
                System.err.println("Ошибка при выполнении команды: " + e.getMessage());
            }
        }
    }
} 