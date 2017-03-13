package seedu.address.model.tasklist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tasklist.UniqueListList.DuplicateListException;
import seedu.address.model.tasklist.UniqueListList.ListNotFoundException;

public class UniqueListListTest {

    public UniqueListList tester = new UniqueListList();
    
    @Test
    public void add() throws DuplicateListException, IllegalValueException {
        assertTrue(tester.toSet().size() == 0);
        tester.add(new TaskList("toAdd"));
        assertTrue(tester.toSet().size() == 1);
    }
    
    @Test
    public void contains() throws DuplicateListException, IllegalValueException {
        assertFalse(tester.contains(new TaskList("empty")));
        tester.add(new TaskList("add"));
        assertTrue(tester.contains(new TaskList("add")));
        assertFalse(tester.contains(new TaskList("not added")));
    }
    
    @Test
    public void update() throws DuplicateListException, IllegalValueException {
        tester.add(new TaskList("add1"));
        tester.updateList(0, new TaskList("editedAdd1"));
        assertFalse(tester.contains(new TaskList("add1")));
        assertTrue(tester.contains(new TaskList("editedAdd1")));
    }
    
    @Test
    public void remove() throws DuplicateListException, IllegalValueException, ListNotFoundException {
        tester.add(new TaskList("add1"));
        assertTrue(tester.contains(new TaskList("add1")));
        assertTrue(tester.remove(new TaskList("add1")));
        assertFalse(tester.contains(new TaskList("add1")));
    }
}
