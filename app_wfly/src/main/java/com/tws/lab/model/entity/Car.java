package com.tws.lab.model.entity;

import jakarta.persistence.*;
import jakarta.json.bind.annotation.JsonbProperty;
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

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "release_year", nullable = false)
    @JsonbProperty("releaseYear")
    private Integer releaseYear;

    @Column(name = "license_plate")
    @JsonbProperty("licensePlate")
    private String licensePlate;

    @Column(name = "owner_phone")
    @JsonbProperty("ownerPhone")
    private String ownerPhone;
} 