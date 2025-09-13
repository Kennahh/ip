package Chauncey.ui;

import Chauncey.task.*;
import Chauncey.exception.ChaunceyException;

import java.util.Scanner;
import java.util.ArrayList;

public class Chauncey {
    private static ArrayList<Task> tasks = new ArrayList<>();

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
                tasks.add(new Todo(task));
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
                tasks.add(new Deadline(deadlineDetails[0].trim(), deadline));
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
                tasks.add(new Event(eventDetails[0].trim(), startTime, endTime));
                break;
            default:
                throw new ChaunceyException("Invalid task type. Please choose among: todo / deadline / event");
            }
            System.out.println("Got it. I've added this task: ");
            tasks.get(tasks.size()-1).outputTaskDetails();
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        } catch (ChaunceyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteTask(String command) {
        try {
            if (tasks.isEmpty()) {
                throw new ChaunceyException("There is no task in the list. Can't do delete command.");
            }
            int taskNumber = getTaskNumber(command);
            String taskDetails = tasks.get(taskNumber - 1).getTaskDetails();
            tasks.remove(taskNumber-1);
            System.out.println("Noted. I've removed this task:");
            System.out.println(taskDetails);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        } catch (ChaunceyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listTasks() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 1; i<= tasks.size(); i++) {
            System.out.print(i + ".");
            tasks.get(i-1).outputTaskDetails();
        }
    }

    private static void markTask(String command) {
        try {
            if (tasks.isEmpty()) {
                throw new ChaunceyException("There is no task in the list. Can't do mark command.");
            }
            int taskNumber = getTaskNumber(command);
            if (tasks.get(taskNumber-1).getStatus()) {
                throw new ChaunceyException("Task " + taskNumber + " is already marked done.");
            }
            tasks.get(taskNumber - 1).markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            tasks.get(taskNumber - 1).outputTaskDetails();
        } catch (ChaunceyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void unmarkTask(String command) {
        try {
            if (tasks.isEmpty()) {
                throw new ChaunceyException("There is no task in the list. Can't do unmark command.");
            }
            int taskNumber = getTaskNumber(command);
            if (!tasks.get(taskNumber-1).getStatus()) {
                throw new ChaunceyException("Task " + taskNumber + " is not done! Can't unmark it.");
            }
            tasks.get(taskNumber - 1).markAsUndone();
            System.out.println("OK, I've marked this task as not done yet:");
            tasks.get(taskNumber - 1).outputTaskDetails();
        } catch (ChaunceyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static int getTaskNumber(String command) throws ChaunceyException {
        String[] commandDetails = command.split(" ");
        if (commandDetails.length < 2) {
            throw new ChaunceyException("I don't know what is the task number for the command. Please also input the task number (formate: <command: delete/mark/unmark> <task number>).");
        }
        String numberInString = commandDetails[commandDetails.length - 1];
        int taskNumber = Integer.parseInt(numberInString);
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new ChaunceyException("Invalid task number. Task number should be between 1 and " + tasks.size());
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
            case "delete":
                deleteTask(command);
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
        System.out.println("List of things I can do: list / add / delete / mark / unmark. If you want to exit, please input \"bye\".");
        System.out.println("What can I do for you?");
    }
}
