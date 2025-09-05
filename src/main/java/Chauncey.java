import java.util.Scanner;

public class Chauncey {
    private static final int MAX_TASKS = 100;
    private static Task[] tasks = new Task[MAX_TASKS];
    private static int numOfTask = 0;

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    private static void addTask() {
        System.out.print("What type of task do you want to add? todo/deadline/event?");
        Scanner in = new Scanner(System.in);
        String type = in.nextLine();
        System.out.println("Please enter the task details (split details by '/'): ");
        String task = in.nextLine();
        switch (type) {
        case "todo":
            tasks[numOfTask] = new Todo(task);
            break;
        case "deadline":
            String[] deadlineDetails = task.split("/");
            String deadline = deadlineDetails[deadlineDetails.length-1].trim();
            tasks[numOfTask] = new Deadline(deadlineDetails[0].trim(), deadline);
            break;
        case "event":
            String[] eventDetails = task.split("/");
            String startTime = eventDetails[eventDetails.length-2].trim();
            String endTime = eventDetails[eventDetails.length-1].trim();
            tasks[numOfTask] = new Event(eventDetails[0].trim(), startTime, endTime);
            break;
        default:
            System.out.println("Invalid task type.");
        }
        numOfTask++;
        System.out.println("Got it. I've added this task: ");
        tasks[numOfTask-1].outputTaskDetails();
        System.out.println("Now you have " + numOfTask + " tasks in the list.");
    }

    private static void removeTask(String command) {
        int taskNumber = getTaskNumber(command);
        String taskDetails = tasks[taskNumber-1].getTaskDetails();
        for (int i=taskNumber-1; i<numOfTask-1; i++) {
            tasks[i] = tasks[i+1];
        }
        numOfTask--;
        System.out.println("removed: " + taskDetails);
    }

    private static void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i<= numOfTask; i++) {
            System.out.print(i + ".");
            tasks[i-1].outputTaskDetails();
        }
    }

    private static void markTask(String command) {
        int taskNumber = getTaskNumber(command);
        tasks[taskNumber-1].markAsDone();
        System.out.println("Nice! I've marked this task as done:");
        tasks[taskNumber-1].outputTaskDetails();
    }

    private static void unmarkTask(String command) {
        int taskNumber = getTaskNumber(command);
        tasks[taskNumber-1].markAsUndone();
        System.out.println("OK, I've marked this task as not done yet:");
        tasks[taskNumber-1].outputTaskDetails();
    }

    private static int getTaskNumber(String command) {
        String[] commandDetails = command.split(" ");
        String numberInString = commandDetails[commandDetails.length - 1];
        int taskNumber = Integer.parseInt(numberInString);
        return taskNumber;
    }

    public static void main(String[] args) {
        // Add greetings.
        printLine();
        printWelcomeMessage();
        printLine();
        System.out.println();

        // Enable echos of commands entered by the user
        Scanner in = new Scanner(System.in);
        String command = in.nextLine().toLowerCase();
        while (!command.equals("bye")) {
            printLine();
            executeCommand(command);
            printLine();
            System.out.println();
            command = in.nextLine();
        }

        // Exit when the user enters "bye"
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    private static void executeCommand(String command) {
        String instruction = command.split(" ")[0];
        switch (instruction) {
        case "list":
            listTasks();
            break;
        case "add":
            addTask();
            break;
        case "remove":
            removeTask(command);
            break;
        case "mark":
            markTask(command);
            break;
        case "unmark":
            unmarkTask(command);
            break;
        default:
            System.out.println("Invalid command.");
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("Hello! I'm Chauncey.");
        System.out.println("List of things I can do: add / remove / list / mark / unmark ");
        System.out.println("What can I do for you?");
    }
}
