//@@author A0143409J

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.lang.reflect.Field;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Event;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyRecurringTask.RecurringMode;
import seedu.address.model.task.RecurringEvent;
import seedu.address.model.task.RecurringTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.Venue;

public class AddCommandParserTest {

    public AddCommandParser theOne = AddCommandParser.getInstance();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addTask_emptyString_incorrectCommand() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) theOne.parse(null);
        assertEquals(field.get(incorrectCommand), String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void addTask_onlyName_correctlyAdd() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) theOne.parse("CS2103 Lecture \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        Task sampleTask = new Task(sampleTaskName, new TaskDate(""), new TaskTime(""),
                new Description(""), new Tag(""), new Venue(""), new Priority(""), false);
        assertEqualTasks((Task) field.get(addCommand), sampleTask);
    }

    @Test
    public void addTask_someFields_correctlyAdd() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) AddCommandParser.getInstance().parse("CS2103 Lecture due/30/03 "
                + "dueT/16:00 d/Interesting #CS2103 \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        TaskDate sampleTaskDate = new TaskDate("30/03");
        TaskTime sampleTaskTime = new TaskTime("16:00");
        Description sampleTaskDescription = new Description("Interesting");
        Tag sampleTaskTag = new Tag("CS2103");
        Task sampleTask = new Task(sampleTaskName, sampleTaskDate, sampleTaskTime,
                sampleTaskDescription, sampleTaskTag, new Venue(""), new Priority(""), false);
        assertEqualTasks((Task) field.get(addCommand), sampleTask);
    }

    @Test
    public void addTask_allFields_correctlyAdd() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) theOne.parse("CS2103 Lecture due/30/3 "
                + "dueT/16:00 d/Interesting #CS2103 @I3 p/3 \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        TaskDate sampleTaskDate = new TaskDate("30/3");
        TaskTime sampleTaskTime = new TaskTime("16:00");
        Description sampleTaskDescription = new Description("Interesting");
        Tag sampleTaskTag = new Tag("CS2103");
        Venue sampleTaskVenue = new Venue("I3");
        Priority sampleTaskPriority = new Priority("3");
        Task sampleTask = new Task(sampleTaskName, sampleTaskDate, sampleTaskTime,
                sampleTaskDescription, sampleTaskTag, sampleTaskVenue, sampleTaskPriority, false);
        assertEqualTasks((Task) field.get(addCommand), sampleTask);
    }


    @Test
    public void addEvent_allFields_correctlyAdd() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) theOne.parse("CS2103 Lecture due/30/3 "
                + "dueT/16:00 d/Interesting #CS2103 @I3 p/3 start/29/3 startT/15:00 \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        TaskDate sampleTaskDate = new TaskDate("30/3");
        TaskTime sampleTaskTime = new TaskTime("16:00");
        TaskDate sampleTaskStartDate = new TaskDate("29/3");
        TaskTime sampleTaskStartTime = new TaskTime("15:00");
        Description sampleTaskDescription = new Description("Interesting");
        Tag sampleTaskTag = new Tag("CS2103");
        Venue sampleTaskVenue = new Venue("I3");
        Priority sampleTaskPriority = new Priority("3");
        Event sampleEvent = new Event(sampleTaskName, sampleTaskStartDate, sampleTaskStartTime,
                sampleTaskDate, sampleTaskTime, sampleTaskDescription, sampleTaskTag,
                sampleTaskVenue, sampleTaskPriority, false);
        assertEqualEvents((Event) field.get(addCommand), sampleEvent);
    }

    @Test
    public void addRecurringTask_allFields_correctlyAdd() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) theOne.parse("CS2103 Lecture due/30/3 "
                + "dueT/16:00 d/Interesting #CS2103 @I3 p/3 f/week \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        TaskDate sampleTaskDate = new TaskDate("30/3");
        TaskTime sampleTaskTime = new TaskTime("16:00");
        Description sampleTaskDescription = new Description("Interesting");
        Tag sampleTaskTag = new Tag("CS2103");
        Venue sampleTaskVenue = new Venue("I3");
        Priority sampleTaskPriority = new Priority("3");
        RecurringMode mode = RecurringMode.WEEK;
        RecurringTask sampleRecurringTask = new RecurringTask(
                sampleTaskName, sampleTaskDate, sampleTaskTime, sampleTaskDescription, sampleTaskTag,
                sampleTaskVenue, sampleTaskPriority, false, mode);
        assertEqualRecurringTasks((RecurringTask) field.get(addCommand), sampleRecurringTask);
    }

    @Test
    public void addRecurringEvent_allFields_correctlyAdd() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) theOne.parse("CS2103 Lecture due/30/3 "
                + "dueT/16:00 d/Interesting #CS2103 @I3 p/3 start/29/3 startT/15:00 f/week \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        TaskDate sampleTaskDate = new TaskDate("30/3");
        TaskTime sampleTaskTime = new TaskTime("16:00");
        TaskDate sampleTaskStartDate = new TaskDate("29/3");
        TaskTime sampleTaskStartTime = new TaskTime("15:00");
        Description sampleTaskDescription = new Description("Interesting");
        Tag sampleTaskTag = new Tag("CS2103");
        Venue sampleTaskVenue = new Venue("I3");
        Priority sampleTaskPriority = new Priority("3");
        RecurringMode mode = RecurringMode.WEEK;
        RecurringEvent sampleRecurringEvent = new RecurringEvent(
                sampleTaskName, sampleTaskStartDate, sampleTaskStartTime,
                sampleTaskDate, sampleTaskTime, sampleTaskDescription, sampleTaskTag,
                sampleTaskVenue, sampleTaskPriority, false, mode);
        assertEqualRecurringEvents((RecurringEvent) field.get(addCommand), sampleRecurringEvent);
    }

    private void assertEqualTasks(Task task1, Task sampleTask) {
        assertEquals(task1.getName(), sampleTask.getName());
        assertEquals(task1.getDate(), sampleTask.getDate());
        assertEquals(task1.getTime(), sampleTask.getTime());
        assertEquals(task1.getDescription(), sampleTask.getDescription());
        assertEquals(task1.getTag(), sampleTask.getTag());
        assertEquals(task1.getVenue(), sampleTask.getVenue());
        assertEquals(task1.getPriority(), sampleTask.getPriority());
        assertEquals(task1.isFavorite(), sampleTask.isFavorite());
        assertEquals(task1.isFinished(), sampleTask.isFinished());
    }

    private void assertEqualRecurringTasks(RecurringTask task1, RecurringTask task2) {
        assertEqualTasks(task1, task2);
        assertEquals(task1.getMode(), task2.getMode());
    }

    private void assertEqualEvents(Event event1, Event event2) {
        assertEqualTasks(event1, event2);
        assertEquals(event1.getStartTime(), event2.getStartTime());
        assertEquals(event1.getStartDate(), event2.getStartDate());
    }

    private void assertEqualRecurringEvents(RecurringEvent event1, RecurringEvent event2) {
        assertEqualEvents(event1, event2);
        assertEquals(event1.getMode(), event2.getMode());
    }
}
