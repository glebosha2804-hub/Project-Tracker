package com.example.tracker.service;

import com.example.tracker.model.Status;
import com.example.tracker.model.Task;
import com.example.tracker.repository.TaskRepo;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepo repo;

    public TaskServiceImpl(TaskRepo repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    public Task markComplete(Long id) {
        Task t = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found: " + id));

        if (t.getStatus() != Status.DONE) {
            t.setStatus(Status.DONE);
            t.setCompletedAt(Instant.now());
            repo.save(t);
        }
        return t; // контроллер ничего не возвращает (204), это безопасно
    }
}
