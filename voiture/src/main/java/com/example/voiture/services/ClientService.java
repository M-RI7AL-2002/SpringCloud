package com.example.voiture.services;

import com.example.voiture.entities.Client;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="CLIENT")
public interface ClientService{
    @GetMapping(path="/api/clients/{id}")
    Client clientById(@PathVariable Long id);
}