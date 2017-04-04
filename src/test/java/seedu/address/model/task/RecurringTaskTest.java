//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyRecurringTask.RecurringMode;
import seedu.address.model.task.ReadOnlyTask.FinishProperty;
import seedu.address.model.task.ReadOnlyTask.RecurringProperty;

public class RecurringTaskTest {

    private RecurringTask tester;
    private static RecurringTask sample;

    @BeforeClass
    public static void oneTimeSetup() throws IllegalValueException {
        sample = new RecurringTask(new Name("tester"), new TaskDate("20/12/2017"),
                new TaskTime(""), new Description(""), new Tag(""), new Venue(""), new Priority(""),
                false, FinishProperty.FINISHED, RecurringMode.MONTH);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor1_validInput_recurringTaskConstructed() throws IllegalValueException {

        tester = new RecurringTask(new Name("tester"), new TaskDate("20/12/2017"),
                new TaskTime("6:00"), new Description(""), new Tag(""), new Venue(""), new Priority(""),
                false, RecurringMode.DAY);
        assertTrue(tester.isRecurring());
        assertFalse(tester.isFinished());
        assertEquals(tester.getRecurringProperty(), RecurringProperty.RECURRING);
        assertEquals(tester.getMode(), RecurringMode.DAY);
        assertEquals(tester.getRecurringPeriod(), "every day");
    }

    @Test
    public void constructor2_validInput_recurringTaskConstructed() throws IllegalValueException {
        tester = new RecurringTask(new Name("tester"), new TaskDate("20/12/2017"),
                new TaskTime(""), new Description(""), new Tag(""), new Venue(""), new Priority(""),
                false, FinishProperty.FINISHED, RecurringMode.MONTH);
        assertTrue(tester.isRecurring());
        assertTrue(tester.isFinished());
        assertEquals(tester.getRecurringProperty(), RecurringProperty.RECURRING);
        assertEquals(tester.getMode(), RecurringMode.MONTH);
        assertEquals(tester.getRecurringPeriod(), "every month");
        assertFalse(tester.getDate().getValue().equals(""));
    }

    @Test
    public void constructor3_validInputAsReadOnlyTask_recurringTaskConstructed()
            throws IllegalValueException {

        tester = new RecurringTask(sample);
        assertTrue(tester.isRecurring());
        assertTrue(tester.isFinished());
        assertEquals(tester.getRecurringProperty(), RecurringProperty.RECURRING);
        assertEquals(tester.getMode(), RecurringMode.MONTH);
        assertEquals(tester.getRecurringPeriod(), "every month");
    }

    @Test
    public void resetData_validInputAsReadOnlyTask_recurringTaskConstructed() throws IllegalValueException {

        tester = new RecurringTask(new Name("tester"), new TaskDate("20/12/2017"),
                new TaskTime("6:00"), new Description(""), new Tag(""), new Venue(""), new Priority(""),
                false, RecurringMode.DAY);
        tester.resetData(sample);
        assertTrue(tester.isRecurring());
        assertTrue(tester.isFinished());
        assertEquals(tester.getRecurringProperty(), RecurringProperty.RECURRING);
        assertEquals(tester.getMode(), RecurringMode.MONTH);
        assertEquals(tester.getRecurringPeriod(), "every month");
        assertFalse(tester.getDate().getValue().equals(""));
    }

    @Test
    public void finishOnce_sampleRecurringTask_dateIsIncreasedByOnePeriod() throws IllegalValueException {

        tester = new RecurringTask(sample);
        tester.finishOnce();
        assertEquals(tester.getDate().getValue(), "20/1/2018");
    }
}
