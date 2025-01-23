package com.tws.lab.command;

import com.tws.lab.soap.CarWebService;
import com.tws.lab.soap.CarCrudException_Exception;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Scanner;

public class UploadImageCommand implements CliCommand {
    private final CarWebService carWebService;

    public UploadImageCommand(CarWebService carWebService) {
        this.carWebService = carWebService;
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.print("Введите ID автомобиля: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите путь к изображению: ");
            String imagePath = scanner.nextLine();

            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                System.out.println("Файл не найден: " + imagePath);
                return;
            }

            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            boolean success = carWebService.uploadCarImage(id, base64Image);
            if (success) {
                System.out.println("Изображение успешно загружено");
            } else {
                System.out.println("Не удалось загрузить изображение");
            }
        } catch (CarCrudException_Exception e) {
            System.out.println("Ошибка: " + e.getFaultInfo().getErrorInfo().getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке изображения: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "uploadImage";
    }

    @Override
    public String getDescription() {
        return "Загрузить изображение для автомобиля";
    }
} 