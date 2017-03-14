package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.Priority;

public class PriorityTest {
    
    @Test
    public void isValidPriority() {
        // invalid name
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("*")); // only non-alphanumeric characters
        assertFalse(Priority.isValidPriority("vital!")); // contains illegal expression

        // valid name
        assertTrue(Priority.isValidPriority("1")); // 1
        assertTrue(Priority.isValidPriority("2")); // 2
        assertTrue(Priority.isValidPriority("3")); // 3
        assertTrue(Priority.isValidPriority("important")); // approved expression with lower case
        assertTrue(Priority.isValidPriority("trivial")); 
        assertTrue(Priority.isValidPriority("normal"));
        assertTrue(Priority.isValidPriority("NORMAL")); // approved expression with upper case
        assertTrue(Priority.isValidPriority("TriVAl")); // approved expression with mix case
    }

}
