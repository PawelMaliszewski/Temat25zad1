package com.temat25zad1;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskTitle;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate taskDeadLine;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    public Task() {
    }

    public Task(String taskTitle, String description, LocalDate taskDeadLine, TaskStatus taskStatus) {
        this.taskTitle = taskTitle;
        this.description = description;
        this.taskDeadLine = taskDeadLine;
        this.taskStatus = taskStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTaskDeadLine() {
        return taskDeadLine;
    }

    public void setTaskDeadLine(LocalDate taskDeadLine) {
        this.taskDeadLine = taskDeadLine;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
