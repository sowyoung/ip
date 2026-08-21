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
            String[] parts = input.split(" ");
            String firstWord = parts[0];

            if (input.equals("list")) { // list out tasks
                System.out.println(space);
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < countTask; i++) {
                    System.out.println((i + 1) + ". " + list[i]);
                }
                System.out.println(space);
            } else if (firstWord.equals("mark")) { // mark task as done
                int idx = Integer.parseInt(parts[1]);
                list[idx - 1].setDone();
                System.out.println(space);
                System.out.println("Nice! I've marked this task as done:\n  " + list[idx - 1]);
                System.out.println(space);
            } else if (firstWord.equals("unmark")) { // mark task as not done
                int idx = Integer.parseInt(parts[1]);
                list[idx - 1].setUndone();
                System.out.println(space);
                System.out.println("OK, I've marked this task as not done yet:\n  " + list[idx - 1]);
                System.out.println(space);
            } else { // add task to list
                list[countTask] = new Task(input);
                countTask += 1;
                System.out.println(space);
                System.out.println("added: " + input);
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
