package com.example.todolist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TodoRepository repository){
        return args->{
            log.info("Preloading" + repository.save(new Todo("Complete todoListServer",false)));
            log.info("Preloading" + repository.save(new Todo("Complete todoListClient",false)));
            log.info("Preloading" + repository.save(new Todo("Find a job, dammit",false)));
        };
    }
}
