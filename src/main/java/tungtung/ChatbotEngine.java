package tungtung;

import java.io.IOException;
import java.util.ArrayList;

/** Executes Tung Tung commands for a user interface. */
public class ChatbotEngine {
    private final Parser parser = new Parser();
    private final Storage storage;
    private final TaskList tasks;

    /** Creates an engine backed by the application's task file. */
    public ChatbotEngine() {
        storage = new Storage("data/tungtung.txt");
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException | SecurityException exception) {
            throw new IllegalStateException("Unable to load saved tasks.", exception);
        }
    }

    /**
     * Executes a command and returns text suitable for displaying in a chat bubble.
     *
     * @param input command entered by the user.
     * @return response produced by Tung Tung.
     */
    public String execute(String input) {
        try {
            String command = input.trim();
            if (command.equals("list")) {
                return formatTasks(tasks.toArrayList(), "Here are your tasks:");
            }
            if (command.startsWith("find ")) {
                return formatTasks(tasks.find(command.substring(5)), "Here are the matching tasks:");
            }
            if (command.startsWith("mark ") || command.startsWith("unmark ")) {
                return updateStatus(command);
            }
            if (command.startsWith("delete ")) {
                return deleteTask(command);
            }

            Task task = parser.parseTask(command);
            tasks.add(task);
            save();
            return "Got it! I've added this task:\n" + task;
        } catch (TungTungException | IOException | SecurityException exception) {
            return "OOPS!!! " + exception.getMessage();
        }
    }

    private String formatTasks(ArrayList<Task> selectedTasks, String heading) {
        if (selectedTasks.isEmpty()) {
            return heading + "\nThere are no tasks to show.";
        }
        StringBuilder response = new StringBuilder(heading);
        for (int index = 0; index < selectedTasks.size(); index++) {
            response.append("\n").append(index + 1).append(". ").append(selectedTasks.get(index));
        }
        return response.toString();
    }

    private String updateStatus(String command) throws TungTungException, IOException {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            throw new TungTungException("Please provide a valid task number.");
        }
        int index = taskIndex(parts[1]);
        Task task = tasks.get(index);
        if (parts[0].equals("mark")) {
            task.setDone();
        } else {
            task.setUndone();
        }
        save();
        return "Updated task:\n" + task;
    }

    private String deleteTask(String command) throws TungTungException, IOException {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            throw new TungTungException("Please provide a valid task number.");
        }
        Task removedTask = tasks.remove(taskIndex(parts[1]));
        save();
        return "Removed task:\n" + removedTask;
    }

    private int taskIndex(String number) throws TungTungException {
        try {
            int index = Integer.parseInt(number) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new TungTungException("Please provide a valid task number.");
            }
            return index;
        } catch (NumberFormatException exception) {
            throw new TungTungException("Please provide a valid task number.");
        }
    }

    private void save() throws IOException {
        storage.save(tasks.toArrayList());
    }
}
