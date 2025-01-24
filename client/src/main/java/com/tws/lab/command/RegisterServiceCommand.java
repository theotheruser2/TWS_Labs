package com.tws.lab.command;

import com.tws.lab.service.JuddiService;
import java.util.Scanner;

public class RegisterServiceCommand implements CliCommand {
    private final JuddiService juddiService;
    private final String serviceUrl;

    public RegisterServiceCommand(JuddiService juddiService, String serviceUrl) {
        this.juddiService = juddiService;
        this.serviceUrl = serviceUrl;
    }

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String getDescription() {
        return "Регистрация сервиса в реестре jUDDI";
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.println("Регистрация сервиса в jUDDI");
            System.out.println("URL сервиса: " + serviceUrl);
            
            System.out.print("Введите имя сервиса (например, CarService): ");
            String serviceName = scanner.nextLine().trim();

            juddiService.registerService(serviceUrl, serviceName);
            System.out.println("Сервис успешно зарегистрирован в jUDDI!");
        } catch (Exception e) {
            System.err.println("Ошибка при регистрации сервиса: " + e.getMessage());
        }
    }
} 