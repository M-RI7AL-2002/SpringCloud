package com.example.gateAway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GateAwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateAwayApplication.class, args);
    }

    @Bean
    RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                // --- CLIENT SERVICE ---
                .route(r -> r.path("/api/clients/**")
                        .uri("lb://CLIENT"))

                // --- VOITURE SERVICE ---
                .route(r -> r.path("/api/voitures/**")
                        .uri("lb://VOITURE"))

                .build();
    }
}
