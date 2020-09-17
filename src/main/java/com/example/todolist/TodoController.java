package com.example.todolist;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/tasks")
public class TodoController {
    private TodoRepository repository;
    TodoController(TodoRepository repository){
        this.repository = repository;
    }
    @GetMapping
    public List<Todo> findAll(){
        return repository.findAll();
    }
    @GetMapping(value = "/{id}")
    public Todo findById(@PathVariable("id") Long id){
        return repository.findById(id).orElseThrow(()->new TodoNotFoundException(id));
    }
    /*@GetMapping(value = "/{title}")
    public List<Todo> findByTitle(@PathVariable("title") String title){
        return repository.findByTitle(title);
    }*/
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id){
        repository.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Todo newTodo){
        return repository.save(newTodo).getId();
    }
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody Todo newTodo){
        repository.findById((id))
                .map(todo->{
                    todo.setTitle(newTodo.getTitle());
                    todo.setCompleted(newTodo.getCompleted());
                    return repository.save(todo);
                })
                .orElseGet(()->{
                    newTodo.setId(id);
                    return repository.save(newTodo);
                });
    }
}
