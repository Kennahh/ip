import java.util.Scanner;

public class Chauncey {
    private static Task[] tasks = new Task[100];
    private static int numOfTask = 0;

    public static void printLine(){
        System.out.println("____________________________________________________________");
    }

    public static void addTask(String command){
        tasks[numOfTask] = new Task(command);
        numOfTask++;
        System.out.println("added: " + command);
    }

    public static void listTasks(){
        for (int i = 1; i<= numOfTask; i++){
            System.out.println(i + ".[" + tasks[i-1].getStatusIcon() + "] " + tasks[i-1].getDescription());
        }
    }

    public static void main(String[] args) {
        // Add greetings.
        printLine();
        System.out.println("Hello! I'm Chauncey.");
        System.out.println("What can I do for you?");
        printLine();
        System.out.println();

        // Enable echos of commands entered by the user
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        while (!command.equals("bye")){
            printLine();
            if (command.equals("list")){
                listTasks();
            }
            else if (command.startsWith("mark")){
                int taskNumber = Character.getNumericValue(command.charAt(command.length()-1));
                tasks[taskNumber-1].markAsDone();
            }
            else if (command.startsWith("unmark")){
                int taskNumber = Character.getNumericValue(command.charAt(command.length()-1));
                tasks[taskNumber-1].markAsUndone();
            }
            else{
                addTask(command);
            }
            printLine();
            System.out.println();
            command = in.nextLine();
        }

        // Exit when the user enters "bye"
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }
}
