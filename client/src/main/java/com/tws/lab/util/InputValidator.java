package com.tws.lab.util;

import java.util.Scanner;

public class InputValidator {
    public static int readIntegerOrExit(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit")) {
                throw new ExitCommandException();
            }
            
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите корректное число или 'exit' для выхода.");
            }
        }
    }

    public static class ExitCommandException extends RuntimeException {
        public ExitCommandException() {
            super("Exit command received");
        }
    }
} 