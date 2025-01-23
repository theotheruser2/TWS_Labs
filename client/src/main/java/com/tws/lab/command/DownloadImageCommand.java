package com.tws.lab.command;

import com.tws.lab.soap.CarWebService;
import com.tws.lab.soap.CarCrudException_Exception;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Scanner;

public class DownloadImageCommand implements CliCommand {
    private final CarWebService carWebService;
    private static final String IMAGES_DIR = "client/images";

    public DownloadImageCommand(CarWebService carWebService) {
        this.carWebService = carWebService;
        new File(IMAGES_DIR).mkdirs();
    }

    @Override
    public void execute(Scanner scanner) {
        try {
            System.out.print("Введите ID автомобиля: ");
            int id = Integer.parseInt(scanner.nextLine());

            String base64Image = carWebService.downloadCarImage(id);
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            String fileName = String.format("%s/car_%d.jpg", IMAGES_DIR, id);
            Files.write(new File(fileName).toPath(), imageBytes);

            System.out.println("Изображение сохранено в: " + fileName);
        } catch (CarCrudException_Exception e) {
            System.out.println("Ошибка: " + e.getFaultInfo().getErrorInfo().getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при скачивании изображения: " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "downloadImage";
    }

    @Override
    public String getDescription() {
        return "Скачать изображение автомобиля";
    }
} 