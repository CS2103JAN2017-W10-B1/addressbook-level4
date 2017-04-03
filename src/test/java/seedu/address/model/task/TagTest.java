//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

public class TagTest {

    @Test
    public void isValidTag_validTagName_falseReturned() throws IllegalValueException {
        // invalid name
        assertFalse(Tag.isValidTagName(" ")); // space only
        assertFalse(Tag.isValidTagName("cs 2103")); // contains space
        assertFalse(Tag.isValidTagName("*NUS*")); // contains non-alphanumeric
    }

    @Test
    public void isValidTag_invalidTagName_trueReturned() throws IllegalValueException {
        // valid name
        assertTrue(Tag.isValidTagName("")); // empty
        assertTrue(Tag.isValidTagName("personal")); // alphabets only
        assertTrue(Tag.isValidTagName("2103")); // digits only
        assertTrue(Tag.isValidTagName("cs2103")); // alphabets and digits only
        assertTrue(Tag.isValidTagName("list")); // reserved word
    }

    @Test
    public void isReservedName_nonReservedName_falseReturned() {
        // non-reserved word
        assertFalse(Tag.isReservedName("")); // empty
        assertFalse(Tag.isReservedName("cs2103")); // non-reserved word
    }

    @Test
    public void isReservedName_reservedName_trueReturned() {
        // reserved word
        assertTrue(Tag.isReservedName("list")); // reserved word
        assertTrue(Tag.isReservedName("faVORite")); // reserved word with mixed case
    }
}
