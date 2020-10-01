package com.example.todolist.configuration;

import com.example.todolist.model.SubTask;
import com.example.todolist.model.Todo;
import com.example.todolist.repositories.SubTaskRepository;
import com.example.todolist.repositories.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TodoRepository todoRepository, SubTaskRepository subTaskRepository){
        return args->{
            log.info("Preloading" + todoRepository.save(new Todo("Complete todoListServer",false)));
            log.info("Preloading" + todoRepository.save(new Todo("Complete todoListClient",false)));
            log.info("Preloading" + todoRepository.save(new Todo("Find a job, dammit",false)));
            Todo task = new Todo("Add subtask",false);
            log.info("Preloading" + todoRepository.save(task));
            SubTask subTask = new SubTask("SubTask",false,task);
            log.info("Preloading" + subTaskRepository.save(subTask));
        };
    }
}
