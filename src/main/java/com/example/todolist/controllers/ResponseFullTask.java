package com.example.todolist.controllers;

import com.example.todolist.model.SubTask;
import com.example.todolist.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseFullTask {
    private Todo task;
    private List<SubTask> subTasks;
}
