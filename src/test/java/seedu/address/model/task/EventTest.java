//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class EventTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor() throws IllegalValueException {

        Event tester1 = new Event(new Name("tester1"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("21/12/2017"), new TaskTime("6:00"), null, null, null, null, false);
        assertEquals(tester1.getDate().getValue(), "21/12/2017");
        assertEquals(tester1.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester1.getTime().getValue(), "6:00");
        assertEquals(tester1.getStartTime().getValue(), "6:00");

        Event tester2 = new Event(new Name("tester2"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("20/12/2017"), new TaskTime("7:00"), null, null, null, null, false);
        assertEquals(tester2.getDate().getValue(), "20/12/2017");
        assertEquals(tester2.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester2.getTime().getValue(), "7:00");
        assertEquals(tester2.getStartTime().getValue(), "6:00");

        Event tester3 = new Event(new Name("tester2"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("20/12/2017"), new TaskTime("6:00"), null, null, null, null, false);
    }

    @Test
    public void constructorInvalid1() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        Event tester2 = new Event(new Name("tester2"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("19/12/2017"), new TaskTime("6:00"), null, null, null, null, false);
    }

    @Test
    public void constructorInvalid2() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        Event tester3 = new Event(new Name("tester3"), new TaskDate("20/12/2017"), new TaskTime("7:00"),
                new TaskDate("20/12/2017"), new TaskTime("6:00"), null, null, null, null, false);
    }
}
