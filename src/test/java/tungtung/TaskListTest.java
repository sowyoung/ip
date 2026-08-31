package tungtung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/** Tests the task collection operations provided by {@link TaskList}. */
class TaskListTest {
    @Test
    void taskList_emptyConstructor_startsEmpty() {
        TaskList tasks = new TaskList();

        assertEquals(0, tasks.size());
    }

    @Test
    void taskList_addAndGet_preservesOrder() {
        TaskList tasks = new TaskList();
        Task first = new ToDo("first");
        Task second = new ToDo("second");

        tasks.add(first);
        tasks.add(second);

        assertEquals(2, tasks.size());
        assertEquals(first, tasks.get(0));
        assertEquals(second, tasks.get(1));
    }

    @Test
    void taskList_indexedAdd_insertsAtRequestedPosition() {
        TaskList tasks = new TaskList();
        Task first = new ToDo("first");
        Task inserted = new ToDo("inserted");
        tasks.add(first);

        tasks.add(0, inserted);

        assertEquals(inserted, tasks.get(0));
        assertEquals(first, tasks.get(1));
    }

    @Test
    void taskList_remove_returnsRemovedTaskAndUpdatesSize() {
        TaskList tasks = new TaskList();
        Task removed = new ToDo("removed");
        tasks.add(removed);

        Task result = tasks.remove(0);

        assertEquals(removed, result);
        assertEquals(0, tasks.size());
    }

    @Test
    void taskList_constructorAndExport_copyCollections() {
        ArrayList<Task> source = new ArrayList<>();
        source.add(new ToDo("saved"));
        TaskList tasks = new TaskList(source);

        ArrayList<Task> exported = tasks.toArrayList();
        assertNotSame(source, exported);
        exported.clear();
        assertEquals(1, tasks.size());
    }
}
