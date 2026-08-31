import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Provides the console user interface for the Tung Tung task manager.
 */
public class TungTung {
    private static final Ui UI = new Ui();
    private static final Parser PARSER = new Parser();
    private static final String DIVIDER = "_____________________________________________________________";
    private static final String BANNER = "  _____          _    _ ______ _____  ______ \n"
            + " / ____|   /\\   | |  | |  ____|  __ \\|  ____|\n"
            + "| (___    /  \\  | |__| | |__  | |__) | |__   \n"
            + " \\___ \\  / /\\ \\ |  __  |  __| |  _  /|  __|  \n"
            + " ____) |/ ____ \\| |  | | |____| | \\ \\| |____ \n"
            + "|_____//_/    \\_\\_|  |_|______|_|  \\_\\______|\n";
    private static final String GREETING = "Hello! Tung Tung Sahere!\nHow can I assist?";
    private static final String FAREWELL = "Bye! Tung Tung Sagone!";
    private static final String INVALID_TASK_NUMBER = "OOPS!!! Please provide a valid task number.";
    private static final String INVALID_DEADLINE = "OOPS!!! Use: deadline DESCRIPTION /by yyyy-MM-dd.";
    private static final String INVALID_EVENT = "OOPS!!! Use: event DESCRIPTION /from yyyy-MM-dd /to yyyy-MM-dd.";
    private static final String INVALID_DATE = "OOPS!!! Dates must use yyyy-MM-dd, for example 2019-10-15.";
    private static final String INVALID_EVENT_DATE_RANGE =
            "OOPS!!! An event's end date cannot be before its start date.";
    private static final String INVALID_FILE_SEPARATOR = "OOPS!!! Task details cannot contain \" | \".";
    private static final String SAVE_ERROR = "OOPS!!! I could not save your tasks to disk.";
    private static final String LOAD_ERROR = "OOPS!!! I could not load your saved tasks. Starting with an empty list.";

    /**
     * Starts Tung Tung and processes commands until the user enters {@code bye}.
     *
     * @param args command-line arguments; they are not used by this application
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage("data/tungtung.txt");

        UI.showGreeting();
        TaskList tasks = loadTasks(storage);
        processCommands(scanner, tasks, storage);
        UI.showFarewell();
    }

    /** Prints the banner and greeting used by {@link Ui}. */
    public static void printGreetingBanner() {
        System.out.println(BANNER);
        System.out.println(GREETING);
    }

    /**
     * Repeatedly reads and processes commands from the user.
     *
     * @param scanner source of console input
     * @param tasks list of tasks to update
     */
    private static void processCommands(Scanner scanner, TaskList tasks, Storage storage) {
        while (scanner.hasNextLine()) {
            System.out.print("  ME: ");
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                return;
            }
            handleCommand(input, tasks, storage);
        }
    }

    /**
     * Routes one command to the method responsible for it.
     *
     * @param input command entered by the user
     * @param tasks list of tasks to inspect or update
     */
    private static void handleCommand(String input, TaskList tasks, Storage storage) {
        Parser.CommandType commandType = PARSER.identify(input);
        if (commandType == Parser.CommandType.LIST) {
            printTaskList(tasks);
        } else if (commandType == Parser.CommandType.MARK) {
            markTask(input, tasks, true, storage);
        } else if (commandType == Parser.CommandType.UNMARK) {
            markTask(input, tasks, false, storage);
        } else if (commandType == Parser.CommandType.DELETE) {
            deleteTask(input, tasks, storage);
        } else {
            addTask(input, tasks, storage);
        }
    }

    /**
     * Displays every task currently stored in the task list.
     *
     * @param tasks tasks to display
     */
    private static void printTaskList(TaskList tasks) {
        printDivider();
        System.out.println("Here are the tasks in your list:");
        for (int index = 0; index < tasks.size(); index++) {
            System.out.println((index + 1) + "." + tasks.get(index));
        }
        printDivider();
    }

    /**
     * Marks the requested task as done or not done.
     *
     * @param input mark or unmark command entered by the user
     * @param tasks tasks to update
     * @param isDone whether the task should be marked as completed
     */
    private static void markTask(String input, TaskList tasks, boolean isDone, Storage storage) {
        Task task;
        try {
            task = getTask(input, tasks);
        } catch (TungTungException exception) {
            printError(exception.getMessage());
            return;
        }
        boolean wasDone = task.isDone;
        if (isDone) {
            task.setDone();
        } else {
            task.setUndone();
        }
        if (!saveTasks(tasks, storage)) {
            if (wasDone) {
                task.setDone();
            } else {
                task.setUndone();
            }
            return;
        }

        printDivider();
        String message = isDone ? "Nice! I've marked this task as done:\n  "
                : "OK, I've marked this task as not done yet:\n  ";
        System.out.println(message + task);
        printDivider();
    }

    /**
     * Removes the requested task and reports the remaining number of tasks.
     *
     * @param input delete command entered by the user
     * @param tasks tasks to update
     */
    private static void deleteTask(String input, TaskList tasks, Storage storage) {
        int taskIndex;
        try {
            taskIndex = getTaskIndex(input, tasks);
        } catch (TungTungException exception) {
            printError(exception.getMessage());
            return;
        }
        Task removedTask = tasks.remove(taskIndex);
        if (!saveTasks(tasks, storage)) {
            tasks.add(taskIndex, removedTask);
            return;
        }
        printDivider();
        System.out.println("Noted. I've removed this task:\n  " + removedTask
                + "\nNow you have " + tasks.size() + " tasks in the list.");
        printDivider();
    }

    /**
     * Extracts the one-based task number from a mark, unmark, or delete command.
     *
     * @param input command containing a task number
     * @return the task number supplied by the user
     */
    private static Task getTask(String input, TaskList tasks) throws TungTungException {
        return tasks.get(getTaskIndex(input, tasks));
    }

    /**
     * Converts a command's one-based task number to a valid list index.
     *
     * @param input command containing a task number
     * @param tasks tasks that can be selected
     * @return the zero-based index of the selected task
     * @throws TungTungException if the command has no valid task number
     */
    private static int getTaskIndex(String input, TaskList tasks) throws TungTungException {
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            throw new TungTungException(INVALID_TASK_NUMBER);
        }
        try {
            int taskNumber = Integer.parseInt(parts[1]);
            if (taskNumber < 1 || taskNumber > tasks.size()) {
                throw new TungTungException(INVALID_TASK_NUMBER);
            }
            return taskNumber - 1;
        } catch (NumberFormatException exception) {
            throw new TungTungException(INVALID_TASK_NUMBER);
        }
    }

    /**
     * Creates a task from an add-task command and adds it to the task list.
     *
     * @param input command entered by the user
     * @param tasks tasks to update
     */
    private static void addTask(String input, TaskList tasks, Storage storage) {
        try {
            Task newTask = PARSER.parseTask(input);
            tasks.add(newTask);
            if (!saveTasks(tasks, storage)) {
                tasks.remove(tasks.size() - 1);
                return;
            }
            printAddedTask(newTask, tasks.size());
        } catch (TungTungException exception) {
            printError(exception.getMessage());
        }
    }

    /**
     * Creates the task described by an add-task command.
     *
     * @param input add-task command entered by the user
     * @return the task created from the command
     * @throws TungTungException if the command is not a supported task command
     */
    private static Task createTask(String input) throws TungTungException {
        if (input.equals("todo")) {
            throw new TungTungException("OOPS!!! There is nothing TODO.");
        }
        if (input.startsWith("todo ")) {
            String description = input.substring(5);
            if (description.isBlank()) {
                throw new TungTungException("OOPS!!! There is nothing TODO.");
            }
            validateTaskText(description);
            return new ToDo(description);
        }
        if (input.equals("deadline")) {
            throw new TungTungException(INVALID_DEADLINE);
        }
        if (input.startsWith("deadline ")) {
            return createDeadline(input.substring(9));
        }
        if (input.equals("event")) {
            throw new TungTungException(INVALID_EVENT);
        }
        if (input.startsWith("event ")) {
            return createEvent(input.substring(6));
        }
        throw new TungTungException("OOPS!!! IDK what u are on about :-(");
    }

    /**
     * Creates a deadline task from its description and deadline text.
     *
     * @param input deadline command text after {@code deadline }
     * @return a new deadline task
     */
    private static Deadline createDeadline(String input) throws TungTungException {
        String[] parts = input.split(" /by ", 2);
        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new TungTungException(INVALID_DEADLINE);
        }
        validateTaskText(parts[0]);
        validateTaskText(parts[1]);
        return new Deadline(parts[0], parseDate(parts[1]));
    }

    /**
     * Creates an event task from its description, start time, and end time.
     *
     * @param input event command text after {@code event }
     * @return a new event task
     */
    private static Event createEvent(String input) throws TungTungException {
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length != 3 || parts[0].isBlank() || parts[1].isBlank() || parts[2].isBlank()) {
            throw new TungTungException(INVALID_EVENT);
        }
        validateTaskText(parts[0]);
        validateTaskText(parts[1]);
        validateTaskText(parts[2]);
        LocalDate from = parseDate(parts[1]);
        LocalDate to = parseDate(parts[2]);
        if (to.isBefore(from)) {
            throw new TungTungException(INVALID_EVENT_DATE_RANGE);
        }
        return new Event(parts[0], from, to);
    }

    /**
     * Parses a user-entered ISO calendar date.
     *
     * @param dateText date entered with a task command
     * @return parsed date
     * @throws TungTungException if the date is not in the required format or is impossible
     */
    private static LocalDate parseDate(String dateText) throws TungTungException {
        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeParseException exception) {
            throw new TungTungException(INVALID_DATE);
        }
    }

    /**
     * Rejects text that would be split into multiple fields by the task-file format.
     *
     * @param text user-entered task detail
     * @throws TungTungException if the text contains the file separator
     */
    private static void validateTaskText(String text) throws TungTungException {
        if (text.contains(" | ")) {
            throw new TungTungException(INVALID_FILE_SEPARATOR);
        }
    }

    /**
     * Saves the current task list after a command changes it.
     *
     * @param tasks tasks to save
     */
    private static boolean saveTasks(TaskList tasks, Storage storage) {
        try {
            storage.save(tasks.toArrayList());
            return true;
        } catch (java.io.IOException | SecurityException exception) {
            printError(SAVE_ERROR);
            return false;
        }
    }

    /**
     * Restores the saved task list when the chatbot starts.
     *
     * @param tasks list that receives the restored tasks
     */
    private static TaskList loadTasks(Storage storage) {
        try {
            return new TaskList(storage.load());
        } catch (java.io.IOException | SecurityException exception) {
            printError(LOAD_ERROR);
            return new TaskList();
        }
    }

    /**
     * Reports that a task was added to the task list.
     *
     * @param task task that was added
     * @param taskCount number of tasks now in the list
     */
    private static void printAddedTask(Task task, int taskCount) {
        printDivider();
        System.out.println("Got it. I've added this task:\n  " + task
                + "\nNow you have " + taskCount + " tasks in the list.");
        printDivider();
    }

    /**
     * Displays an error message in the standard console format.
     *
     * @param message error message to display
     */
    private static void printError(String message) {
        printDivider();
        System.out.println(message);
        printDivider();
    }

    /** Prints the greeting shown when the application starts. */
    private static void printGreeting() {
        printDivider();
        System.out.println(BANNER);
        System.out.println(GREETING);
        printDivider();
    }

    /** Prints the farewell shown when the application exits. */
    private static void printFarewell() {
        printDivider();
        System.out.println(FAREWELL);
        printDivider();
    }

    /** Prints a separator used to make console output easier to read. */
    private static void printDivider() {
        System.out.println(DIVIDER);
    }
}
