//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class PriorityTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidPriority_invalidPriority_falseReturned() {
        // invalid priority
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("*")); // only non-alphanumeric characters
        assertFalse(Priority.isValidPriority("vital!")); // contains illegal expression
        assertFalse(Priority.isValidPriority("trivial")); // not 1, 2, or 3
    }

    @Test
    public void isValidPriority_validPriority_trueReturned() {
        // valid priority
        assertTrue(Priority.isValidPriority("1")); // 1
        assertTrue(Priority.isValidPriority("2")); // 2
        assertTrue(Priority.isValidPriority("3")); // 3
    }

    @Test
    public void convert_validPriorityInString_priorityInDigitReturned() {
        // exact expressions
        assertEquals(Priority.convert(Priority.PRIORITY_IMPORTANT), Priority.PRIORITY_3);
        assertEquals(Priority.convert(Priority.PRIORITY_NORMAL), Priority.PRIORITY_2);
        assertEquals(Priority.convert(Priority.PRIORITY_TRIVIAL), Priority.PRIORITY_1);
        assertEquals(Priority.convert(""), Priority.DEFAULT_PRIORITY);

        // same expressions with different case or mix case
        assertEquals(Priority.convert("TRIVIAL"), Priority.PRIORITY_1);
        assertEquals(Priority.convert("tRiViAl"), Priority.PRIORITY_1);
    }

    @Test
    public void constructor_invalidPriority_exceptionThrown() throws IllegalValueException {
        //invalid name by illegal expression
        thrown.expect(IllegalValueException.class);
        new Priority("vital!");
    }

    @Test
    public void constructor_validPriority_priorityConstructed() throws IllegalValueException {
        //valid name
        Priority tester2a = new Priority(""); // empty string
        Priority tester2b = new Priority(" "); // space
        Priority tester2c = new Priority("1"); // 1, 2, or 3
        Priority tester2d = new Priority("important"); // valid expression with same case
        Priority tester2e = new Priority("NorMal"); // valid expression with different case

        assertEquals(tester2a.getValue(), "2");
        assertEquals(tester2b.getValue(), "2");
        assertEquals(tester2c.getValue(), "1");
        assertEquals(tester2d.getValue(), "3");
        assertEquals(tester2e.getValue(), "2");
    }
}
