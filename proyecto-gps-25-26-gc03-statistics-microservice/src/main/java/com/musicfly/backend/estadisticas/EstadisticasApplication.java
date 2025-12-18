package com.musicfly.backend.estadisticas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EstadisticasApplication {
    public static void main(String[] args) {
        SpringApplication.run(EstadisticasApplication.class, args);
    }
}
