//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class TimeTest {

    @Test
    public void isValidTime() {
        // invalid time
        assertFalse(TaskTime.isValidTime(" ")); // space only
        assertFalse(TaskTime.isValidTime("*")); // non-alphanumeric
        assertFalse(TaskTime.isValidTime("eight")); // alphabets
        assertFalse(TaskTime.isValidTime("1200")); // digits without ":"
        assertFalse(TaskTime.isValidTime("24:00")); // hour exceeds 24
        assertFalse(TaskTime.isValidTime("0:60")); // minutes exceeds 60

        // valid time
        assertTrue(TaskTime.isValidTime("")); // empty
        assertTrue(TaskTime.isValidTime("0:00")); // the hour only use 1 digit
        assertTrue(TaskTime.isValidTime("0:59"));
        assertTrue(TaskTime.isValidTime("00:00")); // the hour can use 2 digits
        assertTrue(TaskTime.isValidTime("23:59")); // latest time
        assertTrue(TaskTime.isValidTime("19:59")); // hour begins with 1
    }
    
    @Test
    public void compareTo() throws IllegalValueException {
        
        TaskTime testerNull = new TaskTime("");
        TaskTime tester1 = new TaskTime("8:00");
        TaskTime tester2 = new TaskTime("18:00");
        TaskTime tester3 = new TaskTime("23:00");
        TaskTime tester4 = new TaskTime("08:00");

        assertTrue(testerNull.compareTo(tester2) > 0);
        assertTrue(tester2.compareTo(testerNull) < 0);
        assertEquals(testerNull.compareTo(testerNull), 0);
        assertTrue(tester1.compareTo(tester2) < 0);
        assertTrue(tester3.compareTo(tester2) > 0);
        assertEquals(tester4.compareTo(tester4), 0);
        assertEquals(tester4.compareTo(tester1), 0);
    }
}
