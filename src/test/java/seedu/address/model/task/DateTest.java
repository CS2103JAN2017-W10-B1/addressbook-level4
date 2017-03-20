//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import seedu.address.model.task.TaskDate;

public class DateTest {

    @Test
    public void isValidDate() {
        Date date = new Date(2010, 12, 20);
        // invalid date
        assertFalse(TaskDate.isValidDate(" ")); // space only
        assertFalse(TaskDate.isValidDate("*")); // non-alphanumeric
        assertFalse(TaskDate.isValidDate("month")); // contains alphabets
        assertFalse(TaskDate.isValidDate("0/1")); // day less than 1
        assertFalse(TaskDate.isValidDate("1/0")); // month less than 1
        assertFalse(TaskDate.isValidDate("1/13")); // month more than 12
        assertFalse(TaskDate.isValidDate("32/1")); // day exceeds limit
        assertFalse(TaskDate.isValidDate("29/2")); // day exceeds limit
        assertFalse(TaskDate.isValidDate("31/4")); // day exceeds limit
        assertFalse(TaskDate.isValidDate("20/11/1995")); // invalid year
        assertFalse(TaskDate.isValidDate("20/12/2020")); // invalid year
        assertFalse(TaskDate.isValidDate("20/12/2017/1111")); // more than 3 parts

        // valid date
        assertTrue(TaskDate.isValidDate("")); // empty
        assertTrue(TaskDate.isValidDate("1/1")); // both day and month are 1 digit
        assertTrue(TaskDate.isValidDate("01/1")); // day is 2 digits
        assertTrue(TaskDate.isValidDate("01/01")); // month is 2 digits beginning with 0
        assertTrue(TaskDate.isValidDate("1/12")); // month is 2 digits beginning with 1
        assertTrue(TaskDate.isValidDate("31/1")); // day is 31 for January
        assertTrue(TaskDate.isValidDate("30/11")); // day is 30 for November
        assertTrue(TaskDate.isValidDate("28/2")); // day is 28 for February
        assertTrue(TaskDate.isValidDate("30/11/2017")); // with valid year
        assertTrue(TaskDate.isValidDate("28/2/2019")); // with valid year
    }
}
