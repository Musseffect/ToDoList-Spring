package com.example.todolist;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id){
        super("Couldn't find Resource with id " + id);
    }
}
