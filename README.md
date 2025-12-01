# Projet Spring Cloud – Gestion de Voitures et Clients

## Description du projet

Ce projet est une démonstration de l’écosystème **Spring Cloud**, utilisant plusieurs microservices pour gérer des voitures et leurs clients. Les services sont intégrés via **Eureka** pour le service discovery et un **API Gateway** pour le routage.

Le projet contient les services suivants :

1. **Client Service** : Application REST pour gérer les clients.
2. **Voiture Service** : Application REST pour gérer les voitures. Ce service utilise **Feign** pour récupérer les informations clients depuis le service Client.
3. **Gateway Service** : Spring Cloud Gateway pour centraliser l’accès aux différents endpoints.
4. **Eureka Server** : Annuaire des microservices permettant la découverte dynamique des services.

---

## Architecture

L’architecture suit le modèle microservices classique avec les interactions suivantes :

- Tous les Applications (Client, Voiture, Gateway) s’enregistrent auprès du **Eureka Server** pour permettre la découverte dynamique.
- Le **Gateway Service** expose une interface unique pour accéder aux différents services via des routes spécifiques (`/api/clients/**`, `/api/voitures/**`).
- Le **Voiture Service** ne stocke pas directement les informations complètes du client. Lorsqu’une voiture est récupérée via l’API (`/api/voitures` ou `/api/voitures/{id}`), il utilise **Feign** pour appeler le service Client et enrichir l’objet voiture avec les données du client correspondant.
- Les voitures sont persistées dans une base de données H2, tandis que les clients sont récupérés en temps réel depuis le service Client.
- Eureka assure que tous les Applications sont visibles et disponibles pour la communication entre eux.

---



---

## Résultat

Lorsqu’on interroge l’API `/api/voitures` :
<img width="1736" height="337" alt="image" src="https://github.com/user-attachments/assets/776675ad-9ad9-4465-a919-fb533a626268" />


- Chaque voiture retourne ses informations propres ainsi que celles de son client associé.
- Toutes les requêtes interservices passent par le service Voiture qui enrichit ses données via Feign.
- Le dashboard Eureka montre que tous les services (Client, Voiture, Gateway) sont enregistrés et actifs, prouvant la découverte de services et l’architecture microservices opérationnelle.
  <img width="1884" height="595" alt="image" src="https://github.com/user-attachments/assets/af5d1214-3161-49bd-8108-c18b46740e21" />

