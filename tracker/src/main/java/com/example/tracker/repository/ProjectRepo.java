package com.example.tracker.repository;
import com.example.tracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProjectRepo extends JpaRepository<Project, Long> {}
