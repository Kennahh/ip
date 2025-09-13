package Chauncey.ui;

import Chauncey.task.*;
import Chauncey.exception.ChaunceyException;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class Chauncey {
    private static final int MAX_TASKS = 100;
    private static Task[] tasks = new Task[MAX_TASKS];
    private static int numOfTask = 0;

    public static void printLine() {
        System.out.println("____________________________________________________________");
    }

    private static void addTask() {
        try {
            System.out.print("What type of task do you want to add? todo/deadline/event?");
            Scanner in = new Scanner(System.in);
            String type = in.nextLine();
            if (type.isEmpty()) {
                throw new ChaunceyException("Task type input can't be empty! Please select: todo/deadline/event");
            }
            System.out.println("Please enter the task details (split details by '/'): ");
            String task = in.nextLine();
            if (task.isEmpty()) {
                throw new ChaunceyException("Task details can't be empty! Please input task details.");
            }
            switch (type) {
            case "todo":
                tasks[numOfTask] = new Todo(task);
                break;
            case "deadline":
                String[] deadlineDetails = task.split("/");
                if (deadlineDetails.length < 2) {
                    throw new ChaunceyException("Task details not enough! Please input task input in this format: task description/task deadline");
                }
                if (deadlineDetails.length > 2) {
                    throw new ChaunceyException("Task details more than expected! Please only input task description and task deadline.");
                }
                String deadline = deadlineDetails[deadlineDetails.length - 1].trim();
                tasks[numOfTask] = new Deadline(deadlineDetails[0].trim(), deadline);
                break;
            case "event":
                String[] eventDetails = task.split("/");
                if (eventDetails.length < 3) {
                    throw new ChaunceyException("Task details not enough! Please input task input in this format: task description/(from) start time/(to) end time");
                }
                if (eventDetails.length > 3) {
                    throw new ChaunceyException("Task details more than expected! Please only input task description, task start time and task end time.");
                }
                String startTime = eventDetails[eventDetails.length - 2].trim();
                String endTime = eventDetails[eventDetails.length - 1].trim();
                tasks[numOfTask] = new Event(eventDetails[0].trim(), startTime, endTime);
                break;
            default:
                System.out.println("Invalid task type.");
            }
            numOfTask++;
            System.out.println("Got it. I've added this task: ");
            tasks[numOfTask - 1].outputTaskDetails();
            System.out.println("Now you have " + numOfTask + " tasks in the list.");
            saveToFile();
        } catch (ChaunceyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void removeTask(String command) {
        try {
            if (numOfTask == 0) {
                throw new ChaunceyException("There is no task in the list. Can't do remove command.");
            }
            int taskNumber = getTaskNumber(command);
            String taskDetails = tasks[taskNumber - 1].getTaskDetails();
            for (int i = taskNumber - 1; i < numOfTask - 1; i++) {
                tasks[i] = tasks[i + 1];
            }
            numOfTask--;
            System.out.println("removed: " + taskDetails);
            saveToFile();
        } catch (ChaunceyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i<= numOfTask; i++) {
            System.out.print(i + ".");
            tasks[i-1].outputTaskDetails();
        }
    }

    private static void markTask(String command) {
        try {
            if (numOfTask == 0) {
                throw new ChaunceyException("There is no task in the list. Can't do mark command.");
            }
            int taskNumber = getTaskNumber(command);
            if (tasks[taskNumber-1].getStatus()) {
                throw new ChaunceyException("Task " + taskNumber + " is already marked done.");
            }
            tasks[taskNumber - 1].markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            tasks[taskNumber - 1].outputTaskDetails();
            saveToFile();
        } catch (ChaunceyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void unmarkTask(String command) {
        try {
            if (numOfTask == 0) {
                throw new ChaunceyException("There is no task in the list. Can't do unmark command.");
            }
            int taskNumber = getTaskNumber(command);
            if (!tasks[taskNumber-1].getStatus()) {
                throw new ChaunceyException("Task " + taskNumber + " is not done! Can't unmark it.");
            }
            tasks[taskNumber - 1].markAsUndone();
            System.out.println("OK, I've marked this task as not done yet:");
            tasks[taskNumber - 1].outputTaskDetails();
            saveToFile();
        } catch (ChaunceyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static int getTaskNumber(String command) throws ChaunceyException {
        String[] commandDetails = command.split(" ");
        if (commandDetails.length < 2) {
            throw new ChaunceyException("I don't know what is the task number for the command. Please also input the task number (formate: <command: remove/mark/unmark> <task number>).");
        }
        String numberInString = commandDetails[commandDetails.length - 1];
        int taskNumber = Integer.parseInt(numberInString);
        if (taskNumber < 1 || taskNumber > numOfTask) {
            throw new ChaunceyException("Invalid task number. Task number should be between 1 and " + numOfTask);
        }
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
        try {
            if (command.isEmpty()) {
                throw new ChaunceyException("Command is empty! Please input a command.");
            }
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
                throw new ChaunceyException("Oh no! I don't know what the command means. Please input a valid command.");
            }
        } catch (ChaunceyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("Hello! I'm Chauncey.");
        System.out.println("Loading from previous data...");
        loadFile();
        System.out.println("List of things I can do: list / add / remove / mark / unmark. If you want to exit, please input \"bye\".");
        System.out.println("What can I do for you?");
    }

    private static void saveToFile() {
        try {
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            FileWriter fw = new FileWriter("./data/Chauncey.txt");
            for (int i=0; i<numOfTask; i++) {
                fw.write(tasks[i].writeToFile() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void loadFile() {
        try {
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File f = new File("./data/Chauncey.txt");
            Scanner fileReader = new Scanner(f);
            String line;
            while (fileReader.hasNext()) {
                line = fileReader.nextLine();
                addTaskFromFile(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found.");
        } catch (ChaunceyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addTaskFromFile(String line) throws ChaunceyException {
        String[] taskDetails = line.split("\\|");
        if (taskDetails.length < 3) {
            throw new ChaunceyException("Corrupted data: insufficient fields in line (at least 3 fields is needed: " + line);
        }
        switch (taskDetails[0].trim()) {
        case "T":
            if (taskDetails.length != 3) {
                throw new ChaunceyException("Corrupted data: Exactly 3 fields is needed for todo.");
            }
            tasks[numOfTask++] = new Todo(taskDetails[2].trim());
            updateTaskStatus(taskDetails[1].trim());
            break;
        case "D":
            if (taskDetails.length != 4) {
                throw new ChaunceyException("Corrupted data: Exactly 4 fields is needed for deadline.");
            }
            tasks[numOfTask++] = new Deadline(taskDetails[2].trim(), taskDetails[3].trim());
            updateTaskStatus(taskDetails[1].trim());
            break;
        case "E":
            if (taskDetails.length != 5) {
                throw new ChaunceyException("Corrupted data: Exactly 5 fields is needed for event.");
            }
            tasks[numOfTask++] = new Event(taskDetails[2].trim(), taskDetails[3].trim(), taskDetails[4].trim());
            updateTaskStatus(taskDetails[1].trim());
            break;
        default:
            throw new ChaunceyException("Task type is invalid.");
        }
    }

    private static void updateTaskStatus(String taskStatus) {
        if (taskStatus.equals("1")) {
            tasks[numOfTask - 1].markAsDone();
        }
    }
}
