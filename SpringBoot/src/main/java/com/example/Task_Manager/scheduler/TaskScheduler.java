package com.example.Task_Manager.scheduler;

import com.example.Task_Manager.model.Task;
import com.example.Task_Manager.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
public class TaskScheduler {

    private static final Logger log = LoggerFactory.getLogger(TaskScheduler.class);
    private final TaskRepository taskRepository;

    @Autowired
    public TaskScheduler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * A scheduled job that runs every day at 1:00 AM.
     * It finds all "PENDING" tasks that are past their due date and marks them as "COMPLETED".
     * The cron expression "0 0 1 * * ?" means:
     * - 0 seconds
     * - 0 minutes
     * - 1 hour (1 AM)
     * - * (every day of the month)
     * - * (every month)
     * - ? (any day of the week)
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void markOverdueTasksAsCompleted() {
        log.info("Running scheduled job: Marking overdue tasks as completed...");

        // Find all tasks with "PENDING" status and a due date before today
        List<Task> overdueTasks = taskRepository.findByStatusAndDueDateBefore("PENDING", LocalDate.now());

        if (overdueTasks.isEmpty()) {
            log.info("No overdue tasks found.");
            return;
        }

        log.info("Found {} overdue tasks. Updating their status to 'COMPLETED'.", overdueTasks.size());

        for (Task task : overdueTasks) {
            task.setStatus("COMPLETED");
            taskRepository.save(task);
            log.info("Task with ID {} has been marked as COMPLETED.", task.getId());
        }

        log.info("Scheduled job finished.");
    }
}

