package com.tws.lab.model.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private String brand;
    private String model;
    private Integer release_year;
    private String license_plate;
    private String owner_phone;
}