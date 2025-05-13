package dev.gin.hexagonal.example;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {Application.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext
public abstract class IntegrationTest {

}
