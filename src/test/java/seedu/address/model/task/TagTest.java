//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

public class TagTest {

    @Test
    public void isValidTag() throws IllegalValueException {
        // invalid name
        assertFalse(Tag.isValidTagName(" ")); // space only
        assertFalse(Tag.isValidTagName("cs 2103")); // contains space
        assertFalse(Tag.isValidTagName("*NUS*")); // contains non-alphanumeric

        // valid name
        assertTrue(Tag.isValidTagName("")); // empty
        assertTrue(Tag.isValidTagName("personal")); // alphabets only
        assertTrue(Tag.isValidTagName("2103")); // digits only
        assertTrue(Tag.isValidTagName("cs2103")); // alphabets and digits only
        assertTrue(Tag.isValidTagName("list")); // reserved word

        // reserved word
        assertTrue(Tag.isReservedName("list")); // reserved word
        assertTrue(Tag.isReservedName("favorite")); // reserved word
    }
}
