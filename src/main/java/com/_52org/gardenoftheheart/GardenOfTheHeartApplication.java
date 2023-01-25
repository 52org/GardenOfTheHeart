package com._52org.gardenoftheheart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GardenOfTheHeartApplication {

    public static void main(String[] args) {
        SpringApplication.run(GardenOfTheHeartApplication.class, args);
    }

}
