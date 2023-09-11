package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;


public abstract class AbstractContainerBaseIntegrationTest {
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @ServiceConnection
    protected static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @BeforeAll
    public static void setup() {
        postgresContainer.start();
    }


    @AfterEach
    public void afterAll() {
        if (postgresContainer.isRunning()) {
            postgresContainer.stop();
        }
    }

}
