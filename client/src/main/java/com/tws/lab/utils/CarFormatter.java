package com.tws.lab.utils;

import com.tws.lab.soap.Car;

public class CarFormatter {
    public static String getTableHeader() {
        return String.format("%-10s | %-10s | %-8s | %-15s | %-17s | %-5s",
                "ID", "Бренд", "Модель", "Номер", "Телефон владельца", "Год выпуска");
    }

    public static String getTableSeparator() {
        StringBuilder separator = new StringBuilder();
        for (int i = 0; i < 85; i++) {
            separator.append("-");
        }
        return separator.toString();
    }

    public static String formatCarAsTableRow(Car car) {
        if (car == null) return "";
        return String.format("%-10d | %-10s | %-8s | %-15s | %-17s | %-5s",
                car.getId(),
                car.getBrand() != null ? car.getBrand() : "Н/Д",
                car.getModel() != null ? car.getModel() : "Н/Д",
                car.getLicensePlate() != null ? car.getLicensePlate() : "Н/Д",
                car.getOwnerPhone() != null ? car.getOwnerPhone() : "Н/Д",
                car.getReleaseYear() != null ? car.getReleaseYear().toString() : "Н/Д");
    }
} 