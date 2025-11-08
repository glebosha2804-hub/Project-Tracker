package com.example.tracker.service;
import com.example.tracker.dto.ProjectStatsDto;
import com.example.tracker.model.Status;
import com.example.tracker.repository.TaskRepo;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class DashboardServiceImpl implements DashboardService {
  private final TaskRepo repo;
  public DashboardServiceImpl(TaskRepo repo){ this.repo = repo; }

  @Override
  public ProjectStatsDto computeProjectStats(Long pid){
    long total = repo.countByProjectId(pid);
    long todo = repo.countByProjectIdAndStatus(pid, Status.TODO);
    long inPr = repo.countByProjectIdAndStatus(pid, Status.IN_PROGRESS);
    long done = repo.countByProjectIdAndStatus(pid, Status.DONE);
    long overdue = repo.countByProjectIdAndStatusNotAndDueDateBefore(pid, Status.DONE, Instant.now());
    int percent = total==0 ? 0 : (int)Math.round(done*100.0/total);
    return new ProjectStatsDto(total,todo,inPr,done,percent,overdue);
  }
}
