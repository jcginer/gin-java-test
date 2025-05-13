package dev.gin.hexagonal.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = "dev.gin.hexagonal.example.*")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
