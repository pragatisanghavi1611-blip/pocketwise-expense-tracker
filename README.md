# Pocketwise Expense Tracker

Spring Boot expense tracker. Java 17, Maven, Thymeleaf, Spring Data JPA and H2.

## Run locally
Run `mvn spring-boot:run` and open http://localhost:8080.

## Render note
The default H2 database is stored on the instance filesystem. On Render's free service, local storage is temporary, so data can be lost on restart or redeploy. This is a demo deployment only; configure durable storage and authentication before entering real financial information.

## Build
`mvn -B -DskipTests package`