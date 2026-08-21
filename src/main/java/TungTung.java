import java.util.Scanner;

public class TungTung {
    public static void main(String[] args) {
        String space = "_____________________________________________________________";
        String banner = "  _____          _    _ ______ _____  ______ \n"
                + " / ____|   /\\   | |  | |  ____|  __ \\|  ____|\n"
                + "| (___    /  \\  | |__| | |__  | |__) | |__   \n"
                + " \\___ \\  / /\\ \\ |  __  |  __| |  _  /|  __|  \n"
                + " ____) |/ ____ \\| |  | | |____| | \\ \\| |____ \n"
                + "|_____//_/    \\_\\_|  |_|______|_|  \\_\\______|\n";
        String greet = "Hello! Tung Tung Sahere!\nHow can I assist?";
        String exit = "Bye! Tung Tung Sagone!";

        Scanner scanner = new Scanner(System.in);
        Task[] list = new Task[100]; // array storing tasks
        int countTask = 0; // track the number of tasks

        // prints out greeting
        System.out.println(space);
        System.out.println(banner);
        System.out.println(greet);
        System.out.println(space);

        System.out.print("  ME: ");
        String input = scanner.nextLine();
        while (!input.equals("bye")) { // enters a loop for chatting
            if (input.equals("list")) { // list out tasks
                System.out.println(space);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < countTask; i++) {
                    System.out.println((i + 1) + ". " + list[i]);
                }
                System.out.println(space);
            } else if (input.startsWith("mark ")) { // mark task as done
                String[] parts = input.split(" ");
                int idx = Integer.parseInt(parts[1]);
                list[idx - 1].setDone();
                System.out.println(space);
                System.out.println("Nice! I've marked this task as done:\n  " + list[idx - 1]);
                System.out.println(space);
            } else if (input.startsWith("unmark ")) { // mark task as not done
                String[] parts = input.split(" ");
                int idx = Integer.parseInt(parts[1]);
                list[idx - 1].setUndone();
                System.out.println(space);
                System.out.println("OK, I've marked this task as not done yet:\n  " + list[idx - 1]);
                System.out.println(space);
            } else { // add task of each type to list
                Task newTask = null;
                if (input.startsWith("todo ")) {
                    String description = input.substring(5);
                    newTask = new ToDo(description);
                } else if (input.startsWith("deadline ")) {
                    String remainingInput = input.substring(9);
                    String[] parts = remainingInput.split(" /by ", 2);
                    String description = parts[0];
                    String by = parts[1];
                    newTask = new Deadline(description, by);
                } else if (input.startsWith("event ")) {
                    String remainingInput = input.substring(6);
                    String[] parts = remainingInput.split(" /from | /to ", 3);
                    String description = parts[0];
                    String from = parts[1];
                    String to = parts[2];
                    newTask = new Event(description, from, to);
                } else {
                    // bad boy
                }

                list[countTask] = newTask;
                countTask += 1;
                System.out.println(space);
                System.out.println("Got it. I've added this task:\n  " + newTask + "\nNow you have " + countTask + " tasks in the list.");
                System.out.println(space);
            }

            System.out.print("  ME: ");
            input = scanner.nextLine();
        }

        // prints out goodbye
        System.out.println(space);
        System.out.println(exit);
        System.out.println(space);
    }
}
