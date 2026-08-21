import java.util.Scanner;
import java.util.ArrayList;

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
        ArrayList<Task> list = new ArrayList<>(); // array storing tasks

        // prints out greeting
        System.out.println(space);
        System.out.println(banner);
        System.out.println(greet);
        System.out.println(space);

        while (true) { // enters a loop for chatting
            System.out.print("  ME: ");
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) { // list out tasks
                System.out.println(space);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + ". " + list.get(i));
                }
                System.out.println(space);
            } else if (input.startsWith("mark ")) { // mark task as done
                String[] parts = input.split(" ");
                int idx = Integer.parseInt(parts[1]);
                list.get(idx - 1).setDone();
                System.out.println(space);
                System.out.println("Nice! I've marked this task as done:\n  " + list.get(idx - 1));
                System.out.println(space);
            } else if (input.startsWith("unmark ")) { // mark task as not done
                String[] parts = input.split(" ");
                int idx = Integer.parseInt(parts[1]);
                list.get(idx - 1).setUndone();
                System.out.println(space);
                System.out.println("OK, I've marked this task as not done yet:\n  " + list.get(idx - 1));
                System.out.println(space);
            } else { // add task of each type to list
                Task newTask = null;

                try {
                    if (input.equals("todo")) {
                        throw new TungTungException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    if (input.startsWith("todo ")) {
                        String description = input.substring(5);
                        if (description.isBlank()) {
                            throw new TungTungException("OOPS!!! The description of a todo cannot be empty.");
                        }
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
                        throw new TungTungException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                    }

                    list.add(newTask);
                    System.out.println(space);
                    System.out.println("Got it. I've added this task:\n  " + newTask
                            + "\nNow you have " + list.size() + " tasks in the list.");
                    System.out.println(space);
                } catch (TungTungException e) {
                    System.out.println(space);
                    System.out.println(e.getMessage());
                    System.out.println(space);
                }
            }
        }

        // prints out goodbye
        System.out.println(space);
        System.out.println(exit);
        System.out.println(space);
    }
}
