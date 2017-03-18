//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeTest {

    @Test
    public void isValidTime() {
        // invalid time
        assertFalse(Time.isValidTime(" ")); // space only
        assertFalse(Time.isValidTime("*")); // non-alphanumeric
        assertFalse(Time.isValidTime("eight")); // alphabets
        assertFalse(Time.isValidTime("1200")); // digits without ":"
        assertFalse(Time.isValidTime("24:00")); // hour exceeds 24
        assertFalse(Time.isValidTime("0:60")); // minutes exceeds 60

        // valid time
        assertTrue(Time.isValidTime("")); // empty
        assertTrue(Time.isValidTime("0:00")); // the hour only use 1 digit
        assertTrue(Time.isValidTime("0:59"));
        assertTrue(Time.isValidTime("00:00")); // the hour can use 2 digits
        assertTrue(Time.isValidTime("23:59")); // latest time
        assertTrue(Time.isValidTime("19:59")); // hour begins with 1
    }
}
