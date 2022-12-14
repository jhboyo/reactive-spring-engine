package com.derek.reactivespring;

import com.derek.reactivespring.product.Item;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class TemplateDatabaseLoader {

    @Bean
    CommandLineRunner initialize(MongoOperations mongoOperations) {
        return args -> {
            mongoOperations.save(new Item("Alf alarm clock", "kids clock", 19.99));
            mongoOperations.save(new Item("Smurf TV tray", "kids tv tray", 24.99));
        };
    }
}
