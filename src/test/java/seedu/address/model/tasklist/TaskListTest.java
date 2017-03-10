package seedu.address.model.tasklist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

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
        assertFalse(list1.getTags() == null);
        assertTrue(list1.getTags().toSet().size() == 0);
        list1.add(new Tag("tag"));
        assertTrue(list1.getTags().toSet().size() == 1);
    }
}
