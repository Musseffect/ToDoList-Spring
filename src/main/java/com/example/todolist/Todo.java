package com.example.todolist;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Todo {
    private @Id @GeneratedValue Long id;
    private String title;
    private boolean completed;
    public Todo(){}
    public Todo(String title,boolean completed){
        this.title = title;
        this.completed = completed;
    }
    public Long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public boolean getCompleted(){
        return completed;
    }
    @Override
    public String toString(){
        return String.format("Task[id=%d, title='%s', completed='%b']",id,title,completed);
    }
    @Override
    public int hashCode(){
        return Objects.hash(this.id,this.title);
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setCompleted(boolean completed){
        this.completed = completed;
    }
    public void setId(Long id){
        this.id = id;
    }
}
