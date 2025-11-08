package com.example.tracker.repository;
import com.example.tracker.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;

public interface TaskRepo extends JpaRepository<Task, Long> {
  long countByProjectId(Long projectId);
  long countByProjectIdAndStatus(Long projectId, Status status);
  long countByProjectIdAndStatusNotAndDueDateBefore(Long projectId, Status notStatus, Instant before);
}
