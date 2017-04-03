//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.ReadOnlyRecurringTask.RecurringMode;

public class DateTest {

    private static TaskDate testerNull;
    private static TaskDate tester1;
    private static TaskDate tester2;
    private static TaskDate tester3;
    private static TaskDate tester4;
    private static TaskDate tester5;
    private static TaskDate tester6;
    private static TaskDate testerToday;
    private static TaskDate testerSunday;
    private static TaskDate testerSunday2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void oneTimeSetup() throws IllegalValueException {
        initialTestCase();
    }

    @After
    public void tearDown() throws IllegalValueException {
        initialTestCase();
    }

    private static void initialTestCase() throws IllegalValueException {
        testerNull = new TaskDate("");
        tester1 = new TaskDate("20/3/2018");
        tester2 = new TaskDate("20/3/2019");
        tester3 = new TaskDate("20/3");
        tester4 = new TaskDate("20/3/2020");
        tester5 = new TaskDate("20/09");
        tester6 = new TaskDate("1/1/2017");
        testerToday = new TaskDate("today");
        testerSunday = new TaskDate("sun");
        testerSunday2 = new TaskDate("sunday");
    }

    @Test
    public void isValidDate_invalidDate_returnFalse() {
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
        assertFalse(TaskDate.isValidDate("mond")); // invalid name in a week
        assertFalse(TaskDate.isValidDate("td")); // invalid name for today
    }

    @Test
    public void isValidDate_validDate_returnTrue() {
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
        assertTrue(TaskDate.isValidDate("Monday")); // day in a week
        assertTrue(TaskDate.isValidDate("mon")); // day in a week in short
        assertTrue(TaskDate.isValidDate("ToDay")); // today
    }

    @Test
    public void getDateString_date_stringCreated() {
        assertEquals(TaskDate.getDateString(tester1.date), "20/3/2018");
    }

    @Test
    public void getDateString_threeFields_stringCreated() {
        assertEquals(TaskDate.getDateString("2018", "3", "20"), "20/3/2018");
    }

    @Test
    public void getDateString_calendar_stringCreated() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tester1.date);
        assertEquals(TaskDate.getDateString(calendar), "20/3/2018");
    }

    @Test
    public void dayInWeek_string_correspondingInt() {
        assertEquals(TaskDate.dayInWeek("Sunday"), 1);
        assertEquals(TaskDate.dayInWeek("saturday"), 7);
        assertEquals(TaskDate.dayInWeek("thu"), 5);
        assertEquals(TaskDate.dayInWeek("random"), -1);
    }

    @Test
    public void dayInWeek_null_assertionErrorThrown() {
        thrown.expect(AssertionError.class);
        assertEquals(TaskDate.dayInWeek(null), 1);
    }

    @Test
    public void todayOrTomorrow_string_correspondingInt() {
        assertEquals(TaskDate.todayOrTomorrow("TODAY"), 0);
        assertEquals(TaskDate.todayOrTomorrow("tmr"), 1);
        assertEquals(TaskDate.todayOrTomorrow("random"), -1);
    }

    @Test
    public void todayOrTomorrow_null_assertionErrorThrown() {
        thrown.expect(AssertionError.class);
        assertEquals(TaskDate.todayOrTomorrow(null), 1);
    }

    @Test
    public void constructor_feb29OfLeapYear_taskDateConstructed() throws IllegalValueException {
        assertTrue(TaskDate.isValidDate("29/2/2020"));
        new TaskDate("29/2/2020");
    }

    @Test
    public void constructor_feb29OfNonLeapYear_taskDateConstructed() throws IllegalValueException {
        // February 29 will pass isValidDate test if no year is given
        assertTrue(TaskDate.isValidDate("29/2"));
        thrown.expect(IllegalValueException.class);
        // but it should throw exception at construction, because next year is 2018
        new TaskDate("29/2");
    }

    @Test
    public void constructor_dayInWeek_taskDateConstructed() throws IllegalValueException {
        new TaskDate("Monday");
        new TaskDate("mon");
        assertEquals(testerSunday, testerSunday2);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void constructor_noYearGiven_constructedWithNearestYear() throws ParseException {

        assertTrue(TaskDate.isValidDate("20/3")); // with valid date before today without year;
        assertEquals(tester3.date.getYear() + 1900, 2018); // the year is added with the value of the next year
        assertEquals(tester1.getValue(), "20/3/2018");

        assertTrue(TaskDate.isValidDate("20/09")); // with valid year after;
        assertEquals(tester5.date.getYear() + 1900, 2017); // the year is added with the value of current year
        assertEquals(tester5.getValue(), ("20/9/2017"));

        // with space only;
        assertEquals(testerNull.date, TaskDate.FORMATTER.parse(TaskDate.INF_DATE)); // date is INF
        assertEquals(testerNull.getValue(), ""); // value is space
    }

    @Test
    public void compareTo_greaterDate_positiveDiffInHoursReturned() throws IllegalValueException {
        assertEquals(testerNull.compareTo(tester1), TaskDate.INF);
        assertEquals(tester2.compareTo(tester1), 365 * 24);
    }

    @Test
    public void compareTo_smallerDate_negativeDiffInHoursReturned() throws IllegalValueException {
        assertEquals(tester1.compareTo(testerNull), -TaskDate.INF);
        assertEquals(tester1.compareTo(tester2), -365 * 24);
        assertEquals(tester1.compareTo(tester4), -731 * 24); // handle leap year
    }

    @Test
    public void compareTo_sameDate_zeroReturned() throws IllegalValueException {
        assertEquals(testerNull.compareTo(testerNull), 0);
        assertEquals(tester1.compareTo(tester3), 0);
    }

    @Test
    public void isPastDue_pastDueTask_trueReturned() {
        assertTrue(tester6.isPastDue());
    }

    @Test
    public void isPastDue_nonPastDueTask_falseReturned() {
        assertFalse(tester2.isPastDue());
        assertFalse(testerNull.isPastDue()); // empty date will never past due
<<<<<<< HEAD
        assertFalse(testerToday.isPastDue()); // TaskDate constructed by "today" will never past due upon construction
        assertFalse(testerSunday.isPastDue());
        // TaskDate constructed by day in a week will never past due upon construction
=======
        // TaskDate constructed by "today" will never past due upon construction
        assertFalse(testerToday.isPastDue());
        // TaskDate constructed by day in a week will never past due upon construction
        assertFalse(testerSunday.isPastDue());
>>>>>>> origin/master
    }

    @Test
    public void addPeriod_validPeriod_aPeriodAdded() throws IllegalValueException {
        tester1.addPeriod(RecurringMode.DAY);
        assertTrue(tester1.getValue().equals("21/3/2018"));

        tester1.addPeriod(RecurringMode.WEEK);
        assertTrue(tester1.getValue().equals("28/3/2018"));

        tester1.addPeriod(RecurringMode.MONTH);
        assertTrue(tester1.getValue().equals("28/4/2018"));
    }

    @Test
    public void addPeriod_null_assertionErrorThrown() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        tester1.addPeriod(null);
    }
}
