
package com.tws.lab;

import com.tws.lab.command.CliCommand;

import java.util.Map;
import java.util.Scanner;

import static com.tws.lab.utils.Util.produceCommands;

public class CliSoapClientApp {
    public static void main(String[] args) {
        String soapUrl = System.getenv("SOAP_SERVICE_URL");
        if (args.length > 0) {
            soapUrl = args[0];
        }

        if (soapUrl == null || soapUrl.isEmpty()) {
            System.out.println("URL SOAP сервиса должен быть задан через переменную окружения или аргумент командной строки.");
            return;
        }

        try {
            Map<String, CliCommand> commands = produceCommands(soapUrl);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Доступные команды:");
            int index = 1;
            for (CliCommand command : commands.values()) {
                System.out.println(index++ + ") " + command.getName() + " - " + command.getDescription());
            }

            while (true) {
                System.out.print("Введите команду: ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("clear")) {
                    clearConsole();
                    continue;
                }
                CliCommand command = null;
                try {
                    int commandIndex = Integer.parseInt(input);
                    if (commandIndex > 0 && commandIndex <= commands.size()) {
                        command = (CliCommand) commands.values().toArray()[commandIndex - 1];
                    }
                } catch (NumberFormatException e) {
                    command = commands.get(input);
                }

                if (command != null) {
                    try {
                        command.execute(scanner);
                    } catch (Exception e) {
                        System.out.println("Не удалось выполнить команду. SOAP сервис недоступен.");
                    }
                } else {
                    System.out.println("Неизвестная команда. Введите 'help' для списка доступных команд.");
                }

                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("SOAP сервис не смог инициализироваться. Команды недоступны.");
        }
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Не удалось очистить консоль.");
        }
    }
}
