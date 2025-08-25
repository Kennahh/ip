import java.util.Scanner;

public class Chauncey {
    private static String[] commands = new String[100];
    private static int numOfCommands = 0;

    public static void printLine(){
        System.out.println("____________________________________________________________");
    }

    public static void addCommand(String command){
        commands[numOfCommands] = command;
        numOfCommands++;
        System.out.println("added: " + command);
    }

    public static void listCommands(){
        for (int i=1; i<=numOfCommands; i++){
            System.out.println(i + ". " + commands[i-1]);
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
                listCommands();
            }
            else{
                addCommand(command);
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
