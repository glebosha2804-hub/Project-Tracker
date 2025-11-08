package com.example.tracker.controller;
import com.example.tracker.dto.ProjectStatsDto;
import com.example.tracker.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/dashboard")
public class DashboardController {
  private final DashboardService service;
  public DashboardController(DashboardService service){ this.service = service; }

  @GetMapping("/project/{projectId}")
  public ProjectStatsDto stats(@PathVariable Long projectId){
    return service.computeProjectStats(projectId);
  }
}
