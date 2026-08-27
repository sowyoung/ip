import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Saves and loads Tung Tung tasks from a text file on the hard disk.
 */
public class Storage {
    private static final Path DATA_FILE = Path.of("data", "tungtung.txt");

    /**
     * Writes every task to the data file, replacing its previous contents.
     *
     * @param tasks tasks to save
     * @throws IOException if the data directory or file cannot be written
     */
    public static void save(ArrayList<Task> tasks) throws IOException {
        ArrayList<String> taskLines = new ArrayList<>();
        for (Task task : tasks) {
            taskLines.add(toFileLine(task));
        }

        Path dataDirectory = DATA_FILE.getParent();
        Files.createDirectories(dataDirectory);
        Path temporaryFile = Files.createTempFile(dataDirectory, "tungtung-", ".tmp");
        try {
            Files.write(temporaryFile, taskLines, StandardCharsets.UTF_8);
            moveIntoPlace(temporaryFile);
        } finally {
            Files.deleteIfExists(temporaryFile);
        }
    }

    /**
     * Loads every task stored in the data file. A missing data file represents an empty task list.
     *
     * @return the tasks reconstructed from the data file
     * @throws IOException if the file cannot be read or contains an invalid task line
     */
    public static ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(DATA_FILE)) {
            return tasks;
        }

        ArrayList<String> taskLines = new ArrayList<>(Files.readAllLines(DATA_FILE, StandardCharsets.UTF_8));
        for (int index = 0; index < taskLines.size(); index++) {
            String taskLine = taskLines.get(index);
            if (!taskLine.isBlank()) {
                tasks.add(fromFileLine(taskLine, index + 1));
            }
        }
        return tasks;
    }

    /**
     * Converts one task to the text format used in the data file.
     *
     * @param task task to convert
     * @return a line containing the task type, completion status, and details
     */
    private static String toFileLine(Task task) throws IOException {
        if (task == null) {
            throw new IOException("Cannot save a missing task.");
        }
        String isDone = task.isDone ? "1" : "0";
        if (task instanceof ToDo) {
            return "T | " + isDone + " | " + task.description;
        }
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D | " + isDone + " | " + task.description + " | " + deadline.by;
        }

        if (task instanceof Event) {
            Event event = (Event) task;
            return "E | " + isDone + " | " + task.description + " | " + event.from + " | " + event.to;
        }
        throw new IOException("Unsupported task type: " + task.getClass().getName());
    }

    /**
     * Reconstructs one task from a saved data-file line.
     *
     * @param taskLine saved task text
     * @param lineNumber one-based line number used in error messages
     * @return the reconstructed task
     * @throws IOException if the line does not match the supported file format
     */
    private static Task fromFileLine(String taskLine, int lineNumber) throws IOException {
        String[] parts = taskLine.split(" \\| ", -1);
        if (parts.length < 3 || !isValidStatus(parts[1]) || parts[2].isBlank()) {
            throw invalidTaskLine(lineNumber);
        }

        Task task;
        switch (parts[0]) {
        case "T":
            if (parts.length != 3) {
                throw invalidTaskLine(lineNumber);
            }
            task = new ToDo(parts[2]);
            break;
        case "D":
            if (parts.length != 4 || parts[3].isBlank()) {
                throw invalidTaskLine(lineNumber);
            }
            task = new Deadline(parts[2], parseDate(parts[3], lineNumber));
            break;
        case "E":
            if (parts.length != 5 || parts[3].isBlank() || parts[4].isBlank()) {
                throw invalidTaskLine(lineNumber);
            }
            LocalDate from = parseDate(parts[3], lineNumber);
            LocalDate to = parseDate(parts[4], lineNumber);
            if (to.isBefore(from)) {
                throw invalidTaskLine(lineNumber);
            }
            task = new Event(parts[2], from, to);
            break;
        default:
            throw invalidTaskLine(lineNumber);
        }

        if (parts[1].equals("1")) {
            task.setDone();
        }
        return task;
    }

    /** Returns whether the saved completion status is supported. */
    private static boolean isValidStatus(String status) {
        return status.equals("0") || status.equals("1");
    }

    /**
     * Parses the ISO date stored in the data file.
     *
     * @param dateText ISO date text
     * @param lineNumber one-based line number used in error messages
     * @return parsed date
     * @throws IOException if the date is invalid
     */
    private static LocalDate parseDate(String dateText, int lineNumber) throws IOException {
        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeParseException exception) {
            throw invalidTaskLine(lineNumber);
        }
    }

    /** Creates a clear error for malformed data without exposing parser details to the user. */
    private static IOException invalidTaskLine(int lineNumber) {
        return new IOException("Invalid task data at line " + lineNumber + ".");
    }

    /**
     * Replaces the old data file only after its complete replacement has been written.
     *
     * @param temporaryFile completed temporary file in the data directory
     * @throws IOException if the data file cannot be replaced
     */
    private static void moveIntoPlace(Path temporaryFile) throws IOException {
        try {
            Files.move(temporaryFile, DATA_FILE, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (java.nio.file.AtomicMoveNotSupportedException exception) {
            Files.move(temporaryFile, DATA_FILE, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
