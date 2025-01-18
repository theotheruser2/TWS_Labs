package com.tws.lab.repository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;

import java.util.Map;

@Getter
public class EntityManagerFactoryProvider {
    private final EntityManagerFactory entityManagerFactory;

    public EntityManagerFactoryProvider(String url, String username, String password) {
        entityManagerFactory = Persistence.createEntityManagerFactory(
                "postgres",
                Map.of(
                        "jakarta.persistence.jdbc.url", url,
                        "jakarta.persistence.jdbc.user", username,
                        "jakarta.persistence.jdbc.password", password
                )
        );
    }
}
