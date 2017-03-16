//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.task.Venue;

public class VenueTest {

    @Test
    public void isValidVenue() {
        // invalid venue
        assertFalse(Venue.isValidVenue(" ")); // space only
        assertFalse(Venue.isValidVenue(" NUS")); // begin with white space
        assertFalse(Venue.isValidVenue("*NUS*")); // contains illegal punctuation

        // valid venue
        assertTrue(Venue.isValidVenue("")); // empty
        assertTrue(Venue.isValidVenue("NUS")); // alphabets only
        assertTrue(Venue.isValidVenue("LT27")); // alphabets and digits
        assertTrue(Venue.isValidVenue("Com2 bus stop")); // contains white spaces in between
        assertTrue(Venue.isValidVenue("PGP #4-2")); // contains "#" and "-"
        assertTrue(Venue.isValidVenue("Prince George's Park")); // contains "'"
    }
}
