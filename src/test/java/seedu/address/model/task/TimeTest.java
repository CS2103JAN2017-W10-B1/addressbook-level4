//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
}
