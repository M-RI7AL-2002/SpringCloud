package com.example.client;

import com.example.client.entities.Client;
import com.example.client.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
    @Bean
    CommandLineRunner initialiserBaseH2(ClientRepository clientRepository) {
        return args -> {
            clientRepository.save(new Client(null, "RIHAL", "Mohamed", LocalDate.of(2002, 05, 10)));
            clientRepository.save(new Client(null, "Chaibi", "Rachid",  LocalDate.of (2002, 12, 22)));
            clientRepository.save(new Client(null, "Najib", "Khalil",LocalDate.of(2002,02,10)));
        };
    }
}
