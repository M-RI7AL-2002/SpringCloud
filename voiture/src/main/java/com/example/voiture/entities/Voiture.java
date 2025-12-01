package com.example.voiture.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voiture {

    @Id
    @GeneratedValue
    private Long id;

    private String marque;
    private String matricule;
    private String model;

    // ONLY this is stored in DB
    private Long clientId;

    // This is fetched from Feign, NOT stored
    @Transient
    private Client client;
}
