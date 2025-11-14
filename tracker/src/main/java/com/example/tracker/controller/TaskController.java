package com.example.tracker.controller;

import com.example.tracker.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
  private final TaskService service;
  public TaskController(TaskService service){ this.service = service; }

  @PutMapping("/{id}/complete")
  @ResponseStatus(HttpStatus.NO_CONTENT)   // 204 No Content
  public void complete(@PathVariable Long id) {
    service.markComplete(id);
  }
}
