package com.example.todolist.repositories;

import com.example.todolist.model.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubTaskRepository extends JpaRepository<SubTask,Long> {
    List<SubTask> findByTaskId(Long taskId);
}
