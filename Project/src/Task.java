import java.util.HashMap;
import java.util.Scanner;

public class Task {

    static HashMap<String, Boolean> tasks = new HashMap<>();

    // Assign Task 
    public static void assignTask(String taskName){
        tasks.put(taskName, false);
        System.out.println("Task '" + taskName + "' added.");
    }

    // Mark Task Complete
    public static void markComplete(String taskName){
        if (tasks.containsKey(taskName)){
            tasks.put(taskName, true);
            System.out.println("Task '" + taskName + "' marked as complete.");
        } else{
            System.out.println("Task not found!");
        }
    }

    // Progress Dashboard
    public static void showDashboard() {
        System.out.println("\n--- Progress Dashboard ---");

        int total = tasks.size();
        int completed = 0;

        for (boolean done : tasks.values()) {
            if (done) completed++;
        }

        int remaining = total - completed;

        System.out.println("Total Tasks: " + total);
        System.out.println("Completed: " + completed);
        System.out.println("Remaining: " + remaining);

        if (total > 0) {
            double percent = (completed * 100.0) / total;
            System.out.println("Progress: " + percent + "%");
        } else {
            System.out.println("Progress: 0%");
        }

        System.out.println("\nTask List:");
        for (String name : tasks.keySet()) {
            String status = tasks.get(name) ? "✔" : "✘";
            System.out.println("- " + name + " [" + status + "]");
        }
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        while (true){
            System.out.println("\n--- Task Tracker ---");
            System.out.println("1. Assign Task");
            System.out.println("2. Mark Task Complete");
            System.out.println("3. Show Progress Dashboard");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1){
                System.out.print("Enter task name: ");
                String name = input.nextLine();
                assignTask(name);
            }
            else if (choice == 2){
                System.out.print("Enter task to mark complete: ");
                String name = input.nextLine();
                markComplete(name);
            }
            else if (choice == 3){
                showDashboard();
            }
            else if (choice == 4){
                System.out.println("Goodbye!");
                break;
            }
            else{
                System.out.println("Invalid option!");
            }
        }

        input.close();
    }
}
