package com.example.client.controllers;


import com.example.client.entities.Client;
import com.example.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping(value = "/clients")
    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    @GetMapping("/clients/{id}")
    public Client findById(@PathVariable Long id) throws Exception {
        return clientRepository.findById(id)
                .orElseThrow(() -> new Exception("Client non trouvé"));
    }

}
