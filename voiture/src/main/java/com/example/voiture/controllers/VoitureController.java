package com.example.voiture.controllers;

import com.example.voiture.entities.Client;
import com.example.voiture.entities.Voiture;
import com.example.voiture.repositories.VoitureRepository;
import com.example.voiture.services.ClientService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VoitureController {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private ClientService clientService;

    // GET ALL
    @GetMapping("/voitures")
    public ResponseEntity<Object> findAll() {
        try {
            List<Voiture> voitures = voitureRepository.findAll();

            // Populate each voiture with client data
            voitures.forEach(v -> {
                if (v.getClientId() != null) {
                    v.setClient(clientService.clientById(v.getClientId()));
                }
            });

            return ResponseEntity.ok(voitures);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching voitures: " + e.getMessage());
        }
    }

    // GET ONE BY ID
    @GetMapping("/voitures/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            Voiture voiture = voitureRepository.findById(id)
                    .orElseThrow(() -> new Exception("Voiture Introuvable"));

            // Populate client via Feign
            voiture.setClient(clientService.clientById(voiture.getClientId()));

            return ResponseEntity.ok(voiture);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Voiture not found with ID: " + id);
        }
    }

    // GET ALL CARS FOR A CLIENT
    @GetMapping("/voitures/client/{id}")
    public ResponseEntity<List<Voiture>> findByClient(@PathVariable Long id) {
        try {
            Client client = clientService.clientById(id);

            if (client == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            List<Voiture> voitures = voitureRepository.findByClientId(id);
            return ResponseEntity.ok(voitures);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // CREATE CAR FOR CLIENT
    @PostMapping("/voitures/{clientId}")
    public ResponseEntity<Object> save(@PathVariable Long clientId, @RequestBody Voiture voiture) {
        try {
            Client client = clientService.clientById(clientId);

            if (client == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Client not found with ID: " + clientId);
            }

            voiture.setClientId(clientId); // store in DB
            voiture.setClient(client);     // attach for response

            Voiture savedVoiture = voitureRepository.save(voiture);
            return ResponseEntity.ok(savedVoiture);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving voiture: " + e.getMessage());
        }
    }

    // UPDATE
    @PutMapping("/voitures/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Voiture updatedVoiture) {
        try {
            Voiture existingVoiture = voitureRepository.findById(id)
                    .orElseThrow(() -> new Exception("Voiture not found with ID: " + id));

            if (updatedVoiture.getMatricule() != null) {
                existingVoiture.setMatricule(updatedVoiture.getMatricule());
            }
            if (updatedVoiture.getMarque() != null) {
                existingVoiture.setMarque(updatedVoiture.getMarque());
            }
            if (updatedVoiture.getModel() != null) {
                existingVoiture.setModel(updatedVoiture.getModel());
            }

            Voiture savedVoiture = voitureRepository.save(existingVoiture);
            return ResponseEntity.ok(savedVoiture);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating voiture: " + e.getMessage());
        }
    }
}
