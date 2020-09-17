package com.example.todolist;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(Long id){
        super("Couldn't find Todo with id " + id);
    }
}
