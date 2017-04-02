//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("cs")); // alphabets only
        assertTrue(Name.isValidName("2103")); // numbers only
        assertTrue(Name.isValidName("cs 2103")); // alphanumeric characters
        assertTrue(Name.isValidName("Cs 2103")); // with capital letters
        assertTrue(Name.isValidName("Cs 2103 at Icube")); // long names
    }
}
