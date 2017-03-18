//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTest {

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(Date.isValidDate(" ")); // space only
        assertFalse(Date.isValidDate("*")); // non-alphanumeric
        assertFalse(Date.isValidDate("month")); // contains alphabets
        assertFalse(Date.isValidDate("0/1")); // day less than 1
        assertFalse(Date.isValidDate("1/0")); // month less than 1
        assertFalse(Date.isValidDate("1/13")); // month more than 12
        assertFalse(Date.isValidDate("32/1")); // day exceeds limit
        assertFalse(Date.isValidDate("29/2")); // day exceeds limit
        assertFalse(Date.isValidDate("31/4")); // day exceeds limit
        assertFalse(Date.isValidDate("20/11/1995")); // invalid year
        assertFalse(Date.isValidDate("20/12/2020")); // invalid year
        assertFalse(Date.isValidDate("20/12/2017/1111")); // more than 3 parts

        // valid date
        assertTrue(Date.isValidDate("")); // empty
        assertTrue(Date.isValidDate("1/1")); // both day and month are 1 digit
        assertTrue(Date.isValidDate("01/1")); // day is 2 digits
        assertTrue(Date.isValidDate("01/01")); // month is 2 digits beginning with 0
        assertTrue(Date.isValidDate("1/12")); // month is 2 digits beginning with 1
        assertTrue(Date.isValidDate("31/1")); // day is 31 for January
        assertTrue(Date.isValidDate("30/11")); // day is 30 for November
        assertTrue(Date.isValidDate("28/2")); // day is 28 for February
        assertTrue(Date.isValidDate("30/11/2017")); // with valid year
        assertTrue(Date.isValidDate("28/2/2019")); // with valid year
    }
}
