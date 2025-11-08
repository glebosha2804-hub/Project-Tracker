package com.example.tracker.dto;

public class ProjectStatsDto {
  private final long total, todo, inProgress, done, overdueCount;
  private final int completionPercent;

  public ProjectStatsDto(long total, long todo, long inProgress, long done,
                         int completionPercent, long overdueCount) {
    this.total = total; this.todo = todo; this.inProgress = inProgress;
    this.done = done; this.completionPercent = completionPercent;
    this.overdueCount = overdueCount;
  }

  public long getTotal() { return total; }
  public long getTodo() { return todo; }
  public long getInProgress() { return inProgress; }
  public long getDone() { return done; }
  public int getCompletionPercent() { return completionPercent; }
  public long getOverdueCount() { return overdueCount; }
}
