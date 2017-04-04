//@@author A0147984L
package seedu.address.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask.FinishProperty;

public class EventTest {

    private static Event tester1;
    private static Event tester2;
    private static Event tester3;
    private static Event tester4;
    private static Event tester5;
    private static Event tester6;
    private static Event tester7;
    private static Event tester8;

    @Before
    public void setup() throws IllegalValueException {
        tester1 = new Event(new Name("tester1"), new TaskDate("20/12/2017"),
                new TaskTime("6:00"), new TaskDate("21/12/2017"), new TaskTime("6:00"),
                new Description(""), new Tag(""), new Venue(""),
                new Priority(""), false, FinishProperty.UNFINISHED);
        tester2 = new Event(new Name("tester2"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("20/12/2017"), new TaskTime("7:00"),
                new Description(""), new Tag(""), new Venue(""),
                new Priority(""), true);
        tester3 = new Event(new Name("tester3"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new Description(""), new Tag(""), new Venue(""),
                new Priority(""), false);
        tester4 = new Event(new Name("tester4"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskTime("6:00"), new Description(""), new Tag(""), new Venue(""),
                new Priority(""), false);
        tester5 = new Event(new Name("tester5"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskTime("10:00"), new Description(""), new Tag(""), new Venue(""),
                new Priority(""), false, FinishProperty.UNFINISHED);
        tester6 = new Event(new Name("tester6"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("20/12/2017"), new TaskTime("7:00"),
                new Description(""), new Tag(""), new Venue(""),
                new Priority(""), false, FinishProperty.UNFINISHED);
        tester7 = new Event(new Name("tester7"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("20/12/2017"), new TaskTime("7:00"),
                new Description(""), new Tag(""), new Venue(""),
                new Priority(""), true, FinishProperty.UNFINISHED);
        tester8 = new Event(tester7);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor1_validInput_eventConstructed() {

        assertEquals(tester3.getDate().getValue(), "20/12/2017");
        assertEquals(tester3.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester3.getTime().getValue(), "6:00");
        assertEquals(tester3.getStartTime().getValue(), "6:00");
    }

    @Test
    public void constructor2_validInput_eventConstructed() {

        assertEquals(tester1.getDate().getValue(), "21/12/2017");
        assertEquals(tester1.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester1.getTime().getValue(), "6:00");
        assertEquals(tester1.getStartTime().getValue(), "6:00");

        assertEquals(tester2.getDate().getValue(), "20/12/2017");
        assertEquals(tester2.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester2.getTime().getValue(), "7:00");
        assertEquals(tester2.getStartTime().getValue(), "6:00");

        assertEquals(tester6.getDate().getValue(), "20/12/2017");
        assertEquals(tester6.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester6.getTime().getValue(), "7:00");
        assertEquals(tester6.getStartTime().getValue(), "6:00");
        assertEquals(tester6.getFavoriteText(), "");
        tester6.setFavorite(true);
        assertEquals(tester6.getFavoriteText(), "Favorite \u2764");

        assertEquals(tester7.getDate().getValue(), "20/12/2017");
        assertEquals(tester7.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester7.getTime().getValue(), "7:00");
        assertEquals(tester7.getStartTime().getValue(), "6:00");
        assertEquals(tester7.getFavoriteText(), "Favorite \u2764");
    }

    @Test
    public void constructor3_validInput_eventConstructed() {
        assertEquals(tester5.getDate().getValue(), "20/12/2017");
        assertEquals(tester5.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester5.getTime().getValue(), "10:00");
        assertEquals(tester5.getStartTime().getValue(), "6:00");
    }

  //@@author A0143409J
    @Test
    public void constructor4_validInput_eventConstructed() {

        assertEquals(tester4.getDate().getValue(), "20/12/2017");
        assertEquals(tester4.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester4.getTime().getValue(), "6:00");
        assertEquals(tester4.getStartTime().getValue(), "6:00");
    }


    @Test
    public void constructor5_validInput_eventConstructed() {

        assertEquals(tester8.getDate().getValue(), "20/12/2017");
        assertEquals(tester8.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester8.getTime().getValue(), "7:00");
        assertEquals(tester8.getStartTime().getValue(), "6:00");
        assertEquals(tester8.getFavoriteText(), "Favorite \u2764");
        assertTrue(tester8.equals(tester7));
    }

    @Test
    public void reset_validInput_eventReset() throws IllegalValueException {

        tester5.resetData(tester2);
        assertEquals(tester5.getDate().getValue(), "20/12/2017");
        assertEquals(tester5.getStartDate().getValue(), "20/12/2017");
        assertEquals(tester5.getTime().getValue(), "7:00");
        assertEquals(tester5.getStartTime().getValue(), "6:00");
    }

    @Test
    public void setter_validInput_correspondingFieldSet() throws IllegalValueException {

        assertEquals(tester2.getFavoriteText(), "Favorite \u2764");
        tester2.setDate(new TaskDate("30/12/2017"));
        assertEquals(tester2.getDate().toString(), "30/12/2017");
        tester2.setFavorite(false);
        assertEquals(tester2.getFavoriteText(), "");
        tester2.setFavorite(true);
        assertEquals(tester2.getFavoriteText(), "Favorite \u2764");
        tester2.setName(new Name("123"));
        assertEquals(tester2.getName().toString(), "123");
        tester2.setDescription(new Description("abc"));
        assertEquals(tester2.getDescription().toString(), "abc");
        tester2.setTag(new Tag("tager"));
        assertEquals(tester2.getTag().toString(), "[tager]");
        tester2.setVenue(new Venue("venue"));
        assertEquals(tester2.getVenue().toString(), "venue");
        tester2.setPriority(new Priority(null));
        assertEquals(tester2.getPriority().toString(), "2");
        tester2.setFinish(true);
        assertEquals(tester2.getFinished(), FinishProperty.FINISHED);
        assertEquals(tester2.getFinishedText(), "Finished");
        tester2.setStartDate(new TaskDate("30/12/2017"));
        assertEquals(tester2.getStartDate().toString(), "30/12/2017");
        tester2.setStartTime(new TaskTime("10:00"));
        assertEquals(tester2.getStartTime().toString(), "10:00");
    }

    //@@author A0147984L
    @Test
    public void constructor1_endDateIsBeforeStartDate_exceptionThrown() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        new Event(new Name("tester2"), new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new TaskDate("19/12/2017"), new TaskTime("6:00"),
                new Description(""), new Tag(""), new Venue(""),
                new Priority(""), false);
    }

    @Test
    public void constructor1_sameDateEndTimeIsBeforeStartTime_exceptionThrown() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        new Event(new Name("tester3"), new TaskDate("20/12/2017"), new TaskTime("7:00"),
                new TaskDate("20/12/2017"), new TaskTime("6:00"),
                new Description(""), new Tag(""), new Venue(""),
                new Priority(""), false);
    }

    @Test
    public void constructor2_endTimeIsBeforeStartTime_exceptionThrown() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        new Event(new Name("tester3"), new TaskDate("20/12/2017"), new TaskTime("7:00"),
                new TaskTime("6:00"), new Description(""), new Tag(""), new Venue(""),
                new Priority(""), false);
    }
}
