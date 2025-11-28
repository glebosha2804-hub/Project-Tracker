package tasktracker;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private final List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        if (task != null) {
            tasks.add(task);
        }
    }

    // Optional convenience if you ever want a quick simple task
    public void addTask(String title) {
        if (title == null || title.trim().isEmpty()) {
            return;
        }
        tasks.add(new Task(title.trim()));
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void markComplete(Task task) {
        if (task != null) {
            task.setCompleted(true);
        }
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public int getTotalCount() {
        return tasks.size();
    }

    public int getCompletedCount() {
        int count = 0;
        for (Task t : tasks) {
            if (t.isCompleted()) {
                count++;
            }
        }
        return count;
    }

    public double getCompletionPercent() {
        int total = getTotalCount();
        if (total == 0) return 0.0;
        return (getCompletedCount() * 100.0) / total;
    }
}
