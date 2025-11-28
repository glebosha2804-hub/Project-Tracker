package tasktracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    private String title;
    private String assignee;      // who is it for
    private LocalDate dueDate;
    private String type;          // what kind of task
    private Priority priority;
    private boolean completed;

    public Task(String title) {
        this(title, "", null, "", Priority.MEDIUM);
    }

    public Task(String title, String assignee, LocalDate dueDate, String type, Priority priority) {
        this.title = title;
        this.assignee = assignee != null ? assignee : "";
        this.dueDate = dueDate;
        this.type = type != null ? type : "";
        this.priority = (priority != null) ? priority : Priority.MEDIUM;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAssignee() {
        return assignee;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getType() {
        return type;
    }

    public Priority getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDueDateAsString() {
        if (dueDate == null) return "";
        return dueDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(completed ? "✔ " : "✘ ");
        sb.append("[").append(priority).append("] ");
        sb.append(title);

        if (dueDate != null) {
            sb.append(" (Due: ").append(getDueDateAsString()).append(")");
        }

        if (!assignee.isBlank()) {
            sb.append(" - For: ").append(assignee);
        }

        if (!type.isBlank()) {
            sb.append(" [").append(type).append("]");
        }

        return sb.toString();
    }
}
