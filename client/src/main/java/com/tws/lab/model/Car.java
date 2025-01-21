package com.tws.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private Integer id;
    private String brand;
    private String model;
    private Integer releaseYear;
    private String licensePlate;
    private String ownerPhone;
} 