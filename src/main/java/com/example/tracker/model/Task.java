package com.example.tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "task")
public class Task {
	
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String assignee;

  @Enumerated(EnumType.STRING)
  private Status status;

  private Instant dueDate;
  private Instant completedAt;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "project_id", nullable = false)
  @JsonIgnore
  private Project project;

  // ===== getters / setters =====
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public String getAssignee() { return assignee; }
  public void setAssignee(String assignee) { this.assignee = assignee; }

  public Status getStatus() { return status; }
  public void setStatus(Status status) { this.status = status; }

  public Instant getDueDate() { return dueDate; }
  public void setDueDate(Instant dueDate) { this.dueDate = dueDate; }

  public Instant getCompletedAt() { return completedAt; }
  public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }

  public Project getProject() { return project; }
  public void setProject(Project project) { this.project = project; }
}
