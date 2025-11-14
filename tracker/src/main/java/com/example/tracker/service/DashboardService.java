package com.example.tracker.service;
import com.example.tracker.dto.ProjectStatsDto;

public interface DashboardService { ProjectStatsDto computeProjectStats(Long projectId); }
