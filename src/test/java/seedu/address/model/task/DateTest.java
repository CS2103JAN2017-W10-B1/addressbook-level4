//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(TaskDate.isValidDate(" ")); // space only
        assertFalse(TaskDate.isValidDate("*")); // non-alphanumeric
        assertFalse(TaskDate.isValidDate("month")); // contains alphabets
        assertFalse(TaskDate.isValidDate("0/1")); // day less than 1
        assertFalse(TaskDate.isValidDate("1/0")); // month less than 1
        assertFalse(TaskDate.isValidDate("1/13")); // month more than 12
        assertFalse(TaskDate.isValidDate("32/1")); // day exceeds limit
        assertFalse(TaskDate.isValidDate("31/4")); // day exceeds limit
        assertFalse(TaskDate.isValidDate("20/11/1995")); // invalid year
        assertFalse(TaskDate.isValidDate("29/2/2021")); // invalid year
        assertFalse(TaskDate.isValidDate("29/2/2021")); // invalid year
        assertFalse(TaskDate.isValidDate("20/12/2017/1111")); // more than 3 parts

        // valid date
        assertTrue(TaskDate.isValidDate("")); // empty
        assertTrue(TaskDate.isValidDate("1/1")); // both day and month are 1 digit
        assertTrue(TaskDate.isValidDate("01/1")); // day is 2 digits
        assertTrue(TaskDate.isValidDate("01/01")); // month is 2 digits beginning with 0
        assertTrue(TaskDate.isValidDate("1/12")); // month is 2 digits beginning with 1
        assertTrue(TaskDate.isValidDate("31/1")); // day is 31 for January
        assertTrue(TaskDate.isValidDate("30/11")); // day is 30 for November
        assertTrue(TaskDate.isValidDate("29/2")); // day is 29 for February
        assertTrue(TaskDate.isValidDate("30/11/2017")); // with valid year
        assertTrue(TaskDate.isValidDate("28/2/2019")); // with valid year
        assertTrue(TaskDate.isValidDate("29/2/2020")); // with valid year
    }

    @Test
    public void testOnFeb29() throws IllegalValueException {

        assertTrue(TaskDate.isValidDate("29/2/2020"));
        TaskDate tester1 = new TaskDate("29/2/2020");

        // Feb 29 will pass isValidDate test if no year is given
        assertTrue(TaskDate.isValidDate("29/2"));
        thrown.expect(IllegalValueException.class);
        // but it should throw exception at construction, because next year is 2018
        TaskDate tester2 = new TaskDate("29/2");
    }
    @Test
    public void testOnBeforeToday() throws IllegalValueException {

        assertTrue(TaskDate.isValidDate("20/3/2017")); // with valid year but before today
        thrown.expect(IllegalValueException.class);
        TaskDate tester1 = new TaskDate("20/3/2017");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testOnValidYear() throws IllegalValueException {

        assertTrue(TaskDate.isValidDate("20/3")); // with valid year without year;
        TaskDate tester1 = new TaskDate("20/3");
        assertEquals(tester1.date.getYear() + 1900, 2018);
        assertEquals(tester1.getValue(), "20/3/2018");

        assertTrue(TaskDate.isValidDate("20/09/2017")); // with valid year after;
        TaskDate tester2 = new TaskDate("20/09/2017");
        assertEquals(tester2.date.getYear() + 1900, 2017);

        assertTrue(TaskDate.isValidDate("20/09/2017")); // with space;
        TaskDate testerNull = new TaskDate("");
        assertEquals(testerNull.date, null);
    }

    @Test
    public void compareTo() throws IllegalValueException {

        TaskDate testerNull = new TaskDate("");
        TaskDate tester1 = new TaskDate("20/3/2018");
        TaskDate tester2 = new TaskDate("20/3/2019");
        TaskDate tester3 = new TaskDate("20/3");
        TaskDate tester4 = new TaskDate("20/3/2020");

        assertEquals(testerNull.compareTo(tester1), TaskDate.INF);
        assertEquals(testerNull.compareTo(testerNull), 0);
        assertEquals(tester1.compareTo(testerNull), -TaskDate.INF);
        assertEquals(tester2.compareTo(tester1), 365);
        assertEquals(tester1.compareTo(tester3), 0);
        assertEquals(tester1.compareTo(tester2), -365);
        assertEquals(tester1.compareTo(tester4), -731); // handle leap year
    }
}
