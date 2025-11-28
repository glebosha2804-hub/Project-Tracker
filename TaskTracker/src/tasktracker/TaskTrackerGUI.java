package tasktracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TaskTrackerGUI extends JFrame {

    private enum Filter {
        ALL, COMPLETED, PENDING
    }

    private final TaskManager taskManager;

    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private JLabel statsLabel;
    private JProgressBar progressBar;

    private Filter currentFilter = Filter.ALL;

    public TaskTrackerGUI(TaskManager taskManager) {
        this.taskManager = taskManager;

        setTitle("Task Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null); // center on screen

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Top: New Task button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton newTaskButton = new JButton("New Task");
        newTaskButton.addActionListener(this::handleNewTask);
        topPanel.add(newTaskButton);
        add(topPanel, BorderLayout.NORTH);

        // Center: task list
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Double-click to mark complete
        taskList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Task selected = taskList.getSelectedValue();
                    if (selected != null) {
                        taskManager.markComplete(selected);
                        refreshView();
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(taskList);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom: filter buttons, action buttons, stats + progress
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        // Filter buttons
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filter:"));

        JRadioButton allButton = new JRadioButton("All", true);
        JRadioButton completedButton = new JRadioButton("Completed");
        JRadioButton pendingButton = new JRadioButton("Pending");

        ButtonGroup group = new ButtonGroup();
        group.add(allButton);
        group.add(completedButton);
        group.add(pendingButton);

        allButton.addActionListener(e -> {
            currentFilter = Filter.ALL;
            refreshView();
        });
        completedButton.addActionListener(e -> {
            currentFilter = Filter.COMPLETED;
            refreshView();
        });
        pendingButton.addActionListener(e -> {
            currentFilter = Filter.PENDING;
            refreshView();
        });

        filterPanel.add(allButton);
        filterPanel.add(completedButton);
        filterPanel.add(pendingButton);

        bottomPanel.add(filterPanel, BorderLayout.NORTH);

        // Buttons: complete / delete
        JPanel buttonPanel = new JPanel();
        JButton completeButton = new JButton("Mark Complete");
        JButton deleteButton = new JButton("Delete Task");

        completeButton.addActionListener(this::handleMarkComplete);
        deleteButton.addActionListener(this::handleDeleteTask);

        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        // Stats + progress bar
        JPanel statusPanel = new JPanel(new BorderLayout(5, 5));
        statsLabel = new JLabel("No tasks yet.");
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        statusPanel.add(statsLabel, BorderLayout.NORTH);
        statusPanel.add(progressBar, BorderLayout.SOUTH);

        bottomPanel.add(statusPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // Initial refresh
        refreshView();
    }

    private void handleNewTask(ActionEvent e) {
        TaskDialog dialog = new TaskDialog(this);
        dialog.setVisible(true);

        Task created = dialog.getCreatedTask();
        if (created != null) {
            taskManager.addTask(created);
            refreshView();
        }
    }

    private void handleMarkComplete(ActionEvent e) {
        Task selected = taskList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a task to mark complete.",
                    "No Task Selected",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        taskManager.markComplete(selected);
        refreshView();
    }

    private void handleDeleteTask(ActionEvent e) {
        Task selected = taskList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "Please select a task to delete.",
                    "No Task Selected",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete: \"" + selected.getTitle() + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            taskManager.removeTask(selected);
            refreshView();
        }
    }

    private boolean shouldShow(Task t) {
        return switch (currentFilter) {
            case ALL -> true;
            case COMPLETED -> t.isCompleted();
            case PENDING -> !t.isCompleted();
        };
    }

    private void refreshView() {
        // Refresh list according to filter
        taskListModel.clear();
        for (Task t : taskManager.getTasks()) {
            if (shouldShow(t)) {
                taskListModel.addElement(t);
            }
        }

        // Refresh stats from ALL tasks
        int total = taskManager.getTotalCount();
        int completed = taskManager.getCompletedCount();
        int remaining = total - completed;
        double percent = taskManager.getCompletionPercent();

        statsLabel.setText(
                "Total: " + total +
                        " | Completed: " + completed +
                        " | Remaining: " + remaining
        );
        progressBar.setValue((int) Math.round(percent));
        progressBar.setString(String.format("%.1f%%", percent));
    }
}
