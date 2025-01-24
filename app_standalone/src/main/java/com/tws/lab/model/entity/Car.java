package com.tws.lab.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Поле brand не может быть пустым")
    @Column(name = "brand", nullable = false)
    private String brand;

    @NotBlank(message = "Поле model не может быть пустым")
    @Column(name = "model", nullable = false)
    private String model;

    @Min(value = 1885, message = "Год выпуска не может быть ранее 1885")
    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Pattern(regexp = "[A-Z]\\d{3}[A-Z]{2}\\d{3}", message = "Регистрационный номер должен иметь следующий формат: А123ВК123 (латиница)")
    @Column(name = "license_plate")
    private String licensePlate;

    @Pattern(regexp = "\\+7\\d{10}", message = "Номер телефона должен иметь следующий формат: +71231231231")
    @Column(name = "owner_phone")
    private String ownerPhone;
} 