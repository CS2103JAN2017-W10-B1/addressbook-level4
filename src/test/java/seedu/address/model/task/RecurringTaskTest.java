//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyRecurringTask.RecurringMode;
import seedu.address.model.task.ReadOnlyTask.FinishProperty;
import seedu.address.model.task.ReadOnlyTask.RecurringProperty;

public class RecurringTaskTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor1() throws IllegalValueException {

        RecurringTask tester1 = new RecurringTask(new Name("tester1"), new TaskDate("20/12/2017"),
                new TaskTime("6:00"), new Description(""), new Tag(""), new Venue(""), new Priority(""),
                false, RecurringMode.DAY);
        assertTrue(tester1.isRecurring());
        assertFalse(tester1.isFinished());
        assertEquals(tester1.getRecurringProperty(), RecurringProperty.RECURRING);
        assertEquals(tester1.getMode(), RecurringMode.DAY);
        assertEquals(tester1.getRecurringPeriod(), "every day");
    }

    @Test
    public void constructor2() throws IllegalValueException {
        RecurringTask tester2 = new RecurringTask(new Name("tester1"), new TaskDate("20/12/2017"),
                new TaskTime(""), new Description(""), new Tag(""), new Venue(""), new Priority(""),
                false, FinishProperty.FINISHED, RecurringMode.MONTH);
        assertTrue(tester2.isRecurring());
        assertTrue(tester2.isFinished());
        assertEquals(tester2.getRecurringProperty(), RecurringProperty.RECURRING);
        assertEquals(tester2.getMode(), RecurringMode.MONTH);
        assertEquals(tester2.getRecurringPeriod(), "every month");
        assertFalse(tester2.getDate().getValue().equals(""));

        RecurringTask tester3 = new RecurringTask(tester2);
        assertTrue(tester3.isRecurring());
        assertTrue(tester3.isFinished());
        assertEquals(tester3.getRecurringProperty(), RecurringProperty.RECURRING);
        assertEquals(tester3.getMode(), RecurringMode.MONTH);
        assertEquals(tester3.getRecurringPeriod(), "every month");
    }

    @Test
    public void resetData() throws IllegalValueException {
        RecurringTask tester2 = new RecurringTask(new Name("tester1"), new TaskDate("20/12/2017"),
                new TaskTime(""), new Description(""), new Tag(""), new Venue(""), new Priority(""),
                false, FinishProperty.FINISHED, RecurringMode.MONTH);

        RecurringTask tester1 = new RecurringTask(new Name("tester1"), new TaskDate("20/12/2017"),
                new TaskTime("6:00"), new Description(""), new Tag(""), new Venue(""), new Priority(""),
                false, RecurringMode.DAY);

        tester1.resetData(tester2);
        assertTrue(tester1.isRecurring());
        assertTrue(tester1.isFinished());
        assertEquals(tester1.getRecurringProperty(), RecurringProperty.RECURRING);
        assertEquals(tester1.getMode(), RecurringMode.MONTH);
        assertEquals(tester1.getRecurringPeriod(), "every month");
        assertFalse(tester1.getDate().getValue().equals(""));
    }

    @Test
    public void finishOnce() throws IllegalValueException {

        RecurringTask tester4 = new RecurringTask(new Name("tester1"), new TaskDate("20/12/2017"),
                new TaskTime("6:00"), new Description(""), new Tag(""), new Venue(""), new Priority(""),
                false, RecurringMode.WEEK);

        tester4.finishOnce();
        assertEquals(tester4.getDate().getValue(), "27/12/2017");
    }
}
