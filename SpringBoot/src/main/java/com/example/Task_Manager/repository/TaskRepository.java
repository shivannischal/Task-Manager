package com.example.Task_Manager.repository;

import com.example.Task_Manager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

//This interface handles all the database operations for Tasks.
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Custom query method to find all tasks with a specific status and a due date
     * before a certain date. Spring Data JPA automatically creates the implementation
     * for this method based on its name.
     *
     * @param status The status of the task (e.g., "PENDING").
     * @param date The date to compare the due date against.
     * @return A list of tasks that match the criteria.
     */
    List<Task> findByStatusAndDueDateBefore(String status, LocalDate date);
}
