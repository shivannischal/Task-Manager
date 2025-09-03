package com.example.Task_Manager.controller;

import com.example.Task_Manager.model.Task;
import com.example.Task_Manager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/tasks") // Base path for all endpoints in this controller
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

     // GET /api/tasks : Get all tasks.
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.findAllTasks();
    }

    // GET /api/tasks/{id} : Get a single task by its ID.
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.findTaskById(id);
        return task.map(ResponseEntity::ok) // If task is present, wrap it in a 200 OK response
                .orElse(ResponseEntity.notFound().build()); // Otherwise, return a 404 Not Found
    }

    // POST /api/tasks : Create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        // To Ensure the status is set for new tasks, e.g., to "PENDING"
        if (task.getStatus() == null || task.getStatus().isEmpty()) {
            task.setStatus("PENDING");
        }
        Task savedTask = taskService.saveTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    // PUT /api/tasks/{id} : Update an existing task.
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Optional<Task> optionalTask = taskService.findTaskById(id);
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setTitle(taskDetails.getTitle());
            existingTask.setDescription(taskDetails.getDescription());
            existingTask.setDueDate(taskDetails.getDueDate());
            existingTask.setStatus(taskDetails.getStatus());
            Task updatedTask = taskService.saveTask(existingTask);
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/tasks/{id} : Delete a task.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}

