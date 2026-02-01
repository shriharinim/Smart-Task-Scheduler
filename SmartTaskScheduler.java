import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalTime;

public class SmartTaskScheduler {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TaskManager manager = new TaskManager();

        while (true) {

            System.out.println("\n===== SMART TASK SCHEDULER =====");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Check Missed Tasks");
            System.out.println("6. Upcoming Task Warning");
            System.out.println("7. Edit Task");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input!");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.print("Task Name: ");
                    String name = sc.nextLine();

                    System.out.print("Due Time (HH:MM): ");
                    String time = sc.nextLine();
                    manager.addTask(name, time);
                    break;

                case 2:
                    manager.viewTasks();
                    break;
                case 3:
                    manager.viewTasks();
                    System.out.print("Enter task number: ");
                    int comp = Integer.parseInt(sc.nextLine());
                    manager.markCompleted(comp);
                    break;

                case 4:
                    manager.viewTasks();
                    System.out.print("Enter task number: ");
                    int del = Integer.parseInt(sc.nextLine());
                    manager.deleteTask(del);
                    break;

                case 5:
                    manager.checkMissedTasks();
                    break;

                case 6:
                    manager.upcomingWarnings();
                    break;

                case 7:
                    manager.viewTasks();
                    System.out.print("Enter task number: ");
                    int editNum = Integer.parseInt(sc.nextLine());
                    System.out.print("New Task Name: ");
                    String newName = sc.nextLine();
                    System.out.print("New Due Time (HH:MM): ");
                    String newTime = sc.nextLine();
                    manager.editTask(editNum, newName, newTime);
                    break;

                case 8:
                    System.out.println("Exiting... Have a productive day!");
                    return;

                default:
                    System.out.println("Enter a number between 1 and 8");
            }
        }
    }
}

class Task {

    private String title;
    private LocalTime dueTime;
    private boolean completed;

    public Task(String title, String time) {
        this.title = title;
        this.dueTime = LocalTime.parse(time);
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public LocalTime getDueTime() {
        return dueTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public void setDueTime(String t) {
        this.dueTime = LocalTime.parse(t);
    }

    public void complete() {
        completed = true;
    }

    public String toString() {
        String status = completed ? "Completed" : "Pending";
        return status + " - " + title + " (Due: " + dueTime + ")";
    }
}

class TaskManager {

    private ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(String name, String time) {
        try {
            tasks.add(new Task(name, time));
            System.out.println("Task added successfully!");
        } catch (Exception e) {
            System.out.println("Invalid time format! Use HH:MM");
        }
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        System.out.println("\nYour Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void markCompleted(int num) {
        if (num < 1 || num > tasks.size()) {
            System.out.println("Invalid task number!");
            return;
        }
        tasks.get(num - 1).complete();
        System.out.println("Task marked as completed!");
    }

    public void deleteTask(int num) {
        if (num < 1 || num > tasks.size()) {
            System.out.println("Invalid task number!");
            return;
        }
        tasks.remove(num - 1);
        System.out.println("Task deleted successfully!");
    }

    public void checkMissedTasks() {
        LocalTime now = LocalTime.now();
        boolean anyMissed = false;

        for (Task t : tasks) {
            if (!t.isCompleted() && t.getDueTime().isBefore(now)) {
                System.out.println("Missed Task: " + t.getTitle() + " (Due " + t.getDueTime() + ")");
                anyMissed = true;
            }
        }

        if (!anyMissed)
            System.out.println("No missed tasks!");
    }

    public void upcomingWarnings() {
        LocalTime now = LocalTime.now();

        for (Task t : tasks) {
            if (!t.isCompleted()) {

                int diff = t.getDueTime().toSecondOfDay() - now.toSecondOfDay();

                if (diff > 0 && diff <= 600) { // 10 minutes
                    System.out.println("Alert: Task '" + t.getTitle() + "' is due soon at " + t.getDueTime());
                }
            }
        }
    }

    public void editTask(int num, String newName, String newTime) {
        if (num < 1 || num > tasks.size()) {
            System.out.println("Invalid task number!");
            return;
        }

        Task t = tasks.get(num - 1);
        t.setTitle(newName);
        t.setDueTime(newTime);
        System.out.println("Task updated successfully!");
    }
}
