import java.util.ArrayList;

/**
 * Stores the tasks in the order that they should be shown to the user.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list containing the supplied tasks.
     *
     * @param tasks tasks to place in the list
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Inserts a task at a particular zero-based position.
     *
     * @param index zero-based insertion position
     * @param task task to insert
     */
    public void add(int index, Task task) {
        tasks.add(index, task);
    }

    /**
     * Returns a task at a particular zero-based position.
     *
     * @param index zero-based task position
     * @return task at the requested position
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes and returns a task at a particular zero-based position.
     *
     * @param index zero-based task position
     * @return removed task
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return task count
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns a copy of the tasks for saving.
     *
     * @return copy of the current tasks
     */
    public ArrayList<Task> toArrayList() {
        return new ArrayList<>(tasks);
    }
}
