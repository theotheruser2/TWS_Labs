package com.tws.lab.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cars")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "release_year")
    private Integer release_year;

    @Column(name = "license_plate")
    private String license_plate;

    @Column(name = "owner_phone")
    private String owner_phone;
    
    @Column(name = "image")
    @Lob
    private String image;
}