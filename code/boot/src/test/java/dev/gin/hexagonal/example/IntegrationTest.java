package dev.gin.hexagonal.example;

import dev.gin.hexagonal.example.domain.service.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {Application.class}, webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles({"standalone", "test"})
@DirtiesContext
public abstract class IntegrationTest {

  @Autowired
  protected PriceRepository rulesRepository;

}
