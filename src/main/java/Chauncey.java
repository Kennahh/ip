import java.util.Scanner;

public class Chauncey {
    public static void printLine(){
        System.out.println("____________________________________________________________");
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
            System.out.println(command);
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
