package tasktracker;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManager manager = new TaskManager();
            new TaskTrackerGUI(manager);
        });
    }
}
