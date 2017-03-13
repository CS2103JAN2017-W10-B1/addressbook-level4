package seedu.address.model.tasklist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;

public class TaskListTest {

    @Test
    public void isValidList() {
        
        assertTrue(TaskList.isValidListName("1"));
    }
    
    @Test
    public void equals() throws IllegalValueException {
        TaskList list1 = new TaskList("a");
        assertFalse(list1.equals(new TaskList("b")));
        assertTrue(list1.equals(new TaskList("a")));
    }
    
    @Test
    public void add() throws IllegalValueException {
        TaskList list1 = new TaskList("a");
        assertFalse(list1.getTasks() == null);
        assertTrue(list1.getTasks().asObservableList().size() == 0);
        Task task1 = new Task(new Name("task1"), null, null, null, null, null, null, false);
        Task task2 = new Task(new Name("task2"), null, null, null, null, null, null, false);
        list1.add(task1);
        list1.add(task2);
        assertTrue(list1.getTasks().asObservableList().size() == 2);
    }
}
