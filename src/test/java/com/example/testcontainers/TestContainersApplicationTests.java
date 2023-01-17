package com.example.testcontainers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestContainersApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp:latest").withExposedPorts(8081);
    private static final GenericContainer<?> devApp = new GenericContainer<>("devapp:latest").withExposedPorts(8080);

    @BeforeAll
    public static void setUp() {
        prodApp.start();
        devApp.start();
    }

    @AfterAll
    public static void setDown() {
        prodApp.stop();
        devApp.stop();
    }

    @Test
    void devAppTest() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(8080) + "/profile", String.class);
        Assertions.assertEquals(forEntity.getBody(), "Current profile is dev");
    }

    @Test
    void prodAppTest() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + prodApp.getMappedPort(8081) + "/profile", String.class);
        Assertions.assertEquals(forEntity.getBody(), "Current profile is production");
    }

}
