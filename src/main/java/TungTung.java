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
        String[] todoList = new String[100];
        int countTask = 0;

        System.out.println(space);
        System.out.println(banner);
        System.out.println(greet);
        System.out.println(space);

        System.out.print("    :");
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                System.out.println(space);
                for (int i = 0; i < countTask; i++) {
                    System.out.println((i + 1) + ". " + todoList[i]);
                }
                System.out.println(space);
            } else {
                todoList[countTask] = input;
                countTask += 1;
                System.out.println(space);
                System.out.println("added: " + input);
                System.out.println(space);
            }

            System.out.print("    :");
            input = scanner.nextLine();
        }

        System.out.println(space);
        System.out.println(exit);
        System.out.println(space);
    }
}
