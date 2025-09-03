package com.example.Task_Manager.service;

import com.example.Task_Manager.model.Task;
import com.example.Task_Manager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Retrieves all tasks from the database.
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    // Finds a single task by its ID.
    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Saves a new task or updates an existing one.
    public Task saveTask(Task task) {
        // Have to add validation logic here before saving.
        return taskRepository.save(task);
    }

    // Deletes a task by its ID.
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
