//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask.FinishProperty;

public class EventTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor1() throws IllegalValueException {

        Event tester1 = new Event(new Name("tester1"), new TaskDate("20/12/2017"),
                new TaskTime("6:00"), new TaskDate("21/12/2017"), new TaskTime("6:00"),
                null, null, null, null, false, FinishProperty.UNFINISHED);
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
    }

//@@author A0143409J
    @Test
    public void constructor2() throws IllegalValueException {
        Event tester3 = new Event(new Name("tester3"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("20/12/2017"), new TaskTime("6:00"), null, null, null, null, false);
        assertEquals(tester3.getDate().getValue(), "20/12/2017");
        assertEquals(tester3.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester3.getTime().getValue(), "6:00");
        assertEquals(tester3.getStartTime().getValue(), "6:00");

        Event tester4 = new Event(new Name("tester4"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskTime("6:00"), null, null, null, null, false);
        assertEquals(tester4.getDate().getValue(), "20/12/2017");
        assertEquals(tester4.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester4.getTime().getValue(), "6:00");
        assertEquals(tester4.getStartTime().getValue(), "6:00");

        Event tester5 = new Event(new Name("tester5"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskTime("7:00"), null, null, null, null, false, FinishProperty.UNFINISHED);
        assertEquals(tester5.getDate().getValue(), "20/12/2017");
        assertEquals(tester5.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester5.getTime().getValue(), "7:00");
        assertEquals(tester5.getStartTime().getValue(), "6:00");

        Event tester6 = new Event(new Name("tester6"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("20/12/2017"), new TaskTime("7:00"), null, null, null, null, false,
                FinishProperty.UNFINISHED);
        assertEquals(tester6.getDate().getValue(), "20/12/2017");
        assertEquals(tester6.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester6.getTime().getValue(), "7:00");
        assertEquals(tester6.getStartTime().getValue(), "6:00");
        assertEquals(tester6.getFavoriteText(), "");
        tester6.setFavorite(true);
        assertEquals(tester6.getFavoriteText(), "Favorite");

        Event tester7 = new Event(new Name("tester7"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("20/12/2017"), new TaskTime("7:00"), null, null, null, null, true,
                FinishProperty.UNFINISHED);
        assertEquals(tester7.getDate().getValue(), "20/12/2017");
        assertEquals(tester7.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester7.getTime().getValue(), "7:00");
        assertEquals(tester7.getStartTime().getValue(), "6:00");
        assertEquals(tester7.getFavoriteText(), "Favorite");

        Event tester8 = new Event(tester7);
        assertEquals(tester8.getDate().getValue(), "20/12/2017");
        assertEquals(tester8.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester8.getTime().getValue(), "7:00");
        assertEquals(tester8.getStartTime().getValue(), "6:00");
        assertEquals(tester8.getFavoriteText(), "Favorite");
        assertTrue(tester8.equals(tester7));
    }
    
    @Test
    public void resetTest() throws IllegalValueException {
        Event tester1 = new Event(new Name("tester1"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("20/12/2017"), new TaskTime("7:00"), null, new Tag("abc"), null, null, false,
                FinishProperty.UNFINISHED);
        assertEquals(tester1.getDate().getValue(), "20/12/2017");
        assertEquals(tester1.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester1.getTime().getValue(), "7:00");
        assertEquals(tester1.getStartTime().getValue(), "6:00");

        Event tester2 = new Event(new Name("tester2"), new TaskDate("30/12/2017"), new TaskTime("12:00"),
                new TaskDate("30/12/2017"), new TaskTime("10:00"), null, new Tag("abc"), null, null, true,
                FinishProperty.UNFINISHED);
        assertEquals(tester2.getDate().getValue(), "30/12/2017");
        assertEquals(tester2.getStartDate().getValue(), "30/12/2017");
        assertEquals(tester2.getTime().getValue(), "10:00");
        assertEquals(tester2.getStartTime().getValue(), "12:00");

        tester2.resetData(tester1);
        assertEquals(tester2.getDate().getValue(), "20/12/2017");
        assertEquals(tester2.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester2.getTime().getValue(), "7:00");
        assertEquals(tester2.getStartTime().getValue(), "6:00");
    }

    @Test
    public void setterTest() throws IllegalValueException {
        Event testEvent = new Event(new Name("tester7"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("20/12/2017"), new TaskTime("7:00"), null, null, null, null, true,
                FinishProperty.UNFINISHED);
        assertEquals(testEvent.getFavoriteText(), "Favorite");
        testEvent.setDate(new TaskDate("30/12/2017"));
        assertEquals(testEvent.getDate().toString(), "30/12/2017");
        testEvent.setFavorite(false);
        assertEquals(testEvent.getFavoriteText(), "");
        testEvent.setFavorite(true);
        assertEquals(testEvent.getFavoriteText(), "Favorite");
        testEvent.setName(new Name("123"));
        assertEquals(testEvent.getName().toString(), "123");
        testEvent.setDescription(new Description("abc"));
        assertEquals(testEvent.getDescription().toString(), "abc");
        testEvent.setTag(new Tag("tager"));
        assertEquals(testEvent.getTag().toString(), "[tager]");
        testEvent.setVenue(new Venue("venue"));
        assertEquals(testEvent.getVenue().toString(), "venue");
        testEvent.setPriority(new Priority(null));
        assertEquals(testEvent.getPriority().toString(), "2");
        testEvent.setFinish(true);
        assertEquals(testEvent.getFinished(), FinishProperty.FINISHED);
        assertEquals(testEvent.getFinishedText(), "Finished");
        testEvent.setStartDate(new TaskDate("30/12/2017"));
        assertEquals(testEvent.getStartDate().toString(), "30/12/2017");
        testEvent.setStartTime(new TaskTime("10:00"));
        assertEquals(testEvent.getStartTime().toString(), "10:00");
    }

    @Test
    public void constructorInvalid1() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        new Event(new Name("tester2"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("19/12/2017"), new TaskTime("6:00"), null, null, null, null, false);
    }

    @Test
    public void constructorInvalid2() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        new Event(new Name("tester3"), new TaskDate("20/12/2017"), new TaskTime("7:00"),
                new TaskDate("20/12/2017"), new TaskTime("6:00"), null, null, null, null, false);
    }

    @Test
    public void constructorInvalid3() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        new Event(new Name("tester3"), new TaskDate("20/12/2017"), new TaskTime("7:00"),
                new TaskTime("6:00"), null, null, null, null, false);
    }
}
