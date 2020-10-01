package com.example.todolist.controllers;

import com.example.todolist.model.SubTask;
import com.example.todolist.model.Todo;
import com.example.todolist.ResourceNotFoundException;
import com.example.todolist.repositories.SubTaskRepository;
import com.example.todolist.repositories.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/tasks")
public class TodoController {
    private TodoRepository todoRepository;
    private SubTaskRepository subTaskRepository;
    TodoController(TodoRepository todoRepository,SubTaskRepository subTaskRepository){
        this.todoRepository = todoRepository;
        this.subTaskRepository = subTaskRepository;
    }
    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping
    public List<Todo> findAll(){

        return todoRepository.findAll();
    }
    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        Todo todo = todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id));
        List<SubTask> subTasks = subTaskRepository.findByTaskId(id);
        return new ResponseEntity<ResponseFullTask>(new ResponseFullTask(todo,subTasks),HttpStatus.OK);
    }
    @PreAuthorize("#oauth2.hasScope('write')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        todoRepository.deleteById(id);
    }

    @PreAuthorize("#oauth2.hasScope('write')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@RequestBody Todo newTodo){
        return todoRepository.save(newTodo);
    }

    @PreAuthorize("#oauth2.hasScope('write')")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody Todo newTodo){
        todoRepository.findById(id)
                .map(todo->{
                    todo.setTitle(newTodo.getTitle());
                    todo.setCompleted(newTodo.isCompleted());
                    return todoRepository.save(todo);
                })
                .orElseGet(()->{
                    newTodo.setId(id);
                    return todoRepository.save(newTodo);
                });
    }
    @PreAuthorize("#oauth2.hasScope('write')")
    @PostMapping(value="{taskId}/subtasks/")
    @ResponseStatus(HttpStatus.CREATED)
    public SubTask addSubTask(@PathVariable("taskId") Long taskId, @RequestBody SubTask newSubTask){
        return todoRepository.findById(taskId)
                .map(task->{
                    newSubTask.setTask(task);
                    return subTaskRepository.save(newSubTask);
                })
                .orElseThrow(()->new ResourceNotFoundException(taskId));
    }
    @PreAuthorize("#oauth2.hasScope('write')")
    @PutMapping(value="{taskId}/subtasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateSubTask(@PathVariable("taskId") Long taskId, @PathVariable("id") Long id,@RequestBody SubTask newSubTask){
        subTaskRepository.findById(id)
                .map(subTask->{
                    subTask.setTitle(newSubTask.getTitle());
                    subTask.setCompleted(newSubTask.isCompleted());
                    return subTaskRepository.save(subTask);
                })
                .orElseGet(()->{
                    return todoRepository.findById(taskId)
                        .map(todo-> {
                            newSubTask.setId(id);
                            newSubTask.setTitle(newSubTask.getTitle());
                            newSubTask.setCompleted(newSubTask.isCompleted());
                            newSubTask.setTask(todo);
                            return subTaskRepository.save(newSubTask);
                        }).orElseThrow(()->new ResourceNotFoundException(taskId));
                });
    }
    @PreAuthorize("#oauth2.hasScope('write')")
    @DeleteMapping(value="{taskId}/subtasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSubTask(@PathVariable("taskId") Long taskId, @PathVariable("id") Long id){
        subTaskRepository.deleteById(id);
    }
}
