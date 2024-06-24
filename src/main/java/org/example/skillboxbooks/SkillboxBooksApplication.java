package org.example.skillboxbooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SkillboxBooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillboxBooksApplication.class, args);
    }

}
