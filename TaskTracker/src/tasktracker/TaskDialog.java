package tasktracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TaskDialog extends JDialog {

    private JTextField titleField;
    private JTextField assigneeField;
    private JTextField dueDateField;
    private JComboBox<String> typeCombo;
    private JComboBox<Task.Priority> priorityCombo;

    private Task createdTask;

    public TaskDialog(JFrame parent) {
        super(parent, "New Task", true);
        initComponents();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        titleField = new JTextField();
        assigneeField = new JTextField();
        dueDateField = new JTextField(); // yyyy-MM-dd

        typeCombo = new JComboBox<>(new String[] {
                "General", "School", "Work", "Personal", "Other"
        });
        typeCombo.setEditable(true);

        priorityCombo = new JComboBox<>(Task.Priority.values());
        priorityCombo.setSelectedItem(Task.Priority.MEDIUM);

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);

        formPanel.add(new JLabel("For (Assignee):"));
        formPanel.add(assigneeField);

        formPanel.add(new JLabel("Due Date (yyyy-MM-dd):"));
        formPanel.add(dueDateField);

        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeCombo);

        formPanel.add(new JLabel("Priority:"));
        formPanel.add(priorityCombo);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(this::handleOk);
        cancelButton.addActionListener(e -> {
            createdTask = null;
            dispose();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void handleOk(ActionEvent e) {
        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a title.",
                    "Missing Title",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String assignee = assigneeField.getText().trim();
        String dueText = dueDateField.getText().trim();
        String type = ((String) typeCombo.getSelectedItem());
        Task.Priority priority = (Task.Priority) priorityCombo.getSelectedItem();

        LocalDate dueDate = null;
        if (!dueText.isEmpty()) {
            try {
                dueDate = LocalDate.parse(dueText, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid date format. Use yyyy-MM-dd.",
                        "Invalid Date",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        createdTask = new Task(title, assignee, dueDate, type, priority);
        dispose();
    }

    public Task getCreatedTask() {
        return createdTask;
    }
}
