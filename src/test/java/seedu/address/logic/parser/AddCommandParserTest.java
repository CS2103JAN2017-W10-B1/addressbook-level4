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
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.Venue;

public class AddCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void nullTest() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) AddCommandParser.parse(null);
        assertEquals(incorrectCommand.feedbackToUser, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void nameOnlyTaskTest() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) AddCommandParser.parse("CS2103 Lecture \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        Task sampleTask = new Task(sampleTaskName, new TaskDate(""), new TaskTime(""),
                new Description(""), new Tag(""), new Venue(""), new Priority(""), false, false);
        assertEqualTasks((Task) field.get(addCommand), sampleTask);
    }

    @Test
    public void nameAndDateTaskTest() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) AddCommandParser.parse("CS2103 Lecture due/10/3 \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        TaskDate sampleTaskDate = new TaskDate("10/3");
        Task sampleTask = new Task(sampleTaskName, sampleTaskDate, new TaskTime(""),
                new Description(""), new Tag(""), new Venue(""), new Priority(""), false, false);
        assertEqualTasks((Task) field.get(addCommand), sampleTask);
    }

    @Test
    public void nameDateTimeTaskTest() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) AddCommandParser.parse("CS2103 Lecture due/30/3 dueT/16:00 \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        TaskDate sampleTaskDate = new TaskDate("30/3");
        TaskTime sampleTaskTime = new TaskTime("16:00");
        Task sampleTask = new Task(sampleTaskName, sampleTaskDate, sampleTaskTime,
                new Description(""), new Tag(""), new Venue(""), new Priority(""), false, false);
        assertEqualTasks((Task) field.get(addCommand), sampleTask);
    }

    @Test
    public void nameDateTimeDescriptionTaskTest() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) AddCommandParser.parse("CS2103 Lecture due/30/3 dueT/16:00"
                + "d/Interesting \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        TaskDate sampleTaskDate = new TaskDate("30/3");
        TaskTime sampleTaskTime = new TaskTime("16:00");
        Description sampleTaskDescription = new Description("Interesting");
        Task sampleTask = new Task(sampleTaskName, sampleTaskDate, sampleTaskTime,
                sampleTaskDescription, new Tag(""), new Venue(""), new Priority(""), false, false);
        assertEqualTasks((Task) field.get(addCommand), sampleTask);
    }

    @Test
    public void nameDateTimeDescriptionTagTaskTest() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) AddCommandParser.parse("CS2103 Lecture due/30/03 "
                + "dueT/16:00 d/Interesting #CS2103 \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        TaskDate sampleTaskDate = new TaskDate("30/03");
        TaskTime sampleTaskTime = new TaskTime("16:00");
        Description sampleTaskDescription = new Description("Interesting");
        Tag sampleTaskTag = new Tag("CS2103");
        Task sampleTask = new Task(sampleTaskName, sampleTaskDate, sampleTaskTime,
                sampleTaskDescription, sampleTaskTag, new Venue(""), new Priority(""), false, false);
        assertEqualTasks((Task) field.get(addCommand), sampleTask);
    }

    @Test
    public void nameDateTimeDescriptionTagVenueTaskTest() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) AddCommandParser.parse("CS2103 Lecture due/30/3 "
                + "dueT/16:00 d/Interesting #CS2103 @I3 \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        TaskDate sampleTaskDate = new TaskDate("30/3");
        TaskTime sampleTaskTime = new TaskTime("16:00");
        Description sampleTaskDescription = new Description("Interesting");
        Tag sampleTaskTag = new Tag("CS2103");
        Venue sampleTaskVenue = new Venue("I3");
        Task sampleTask = new Task(sampleTaskName, sampleTaskDate, sampleTaskTime,
                sampleTaskDescription, sampleTaskTag, sampleTaskVenue, new Priority(""), false, false);
        assertEqualTasks((Task) field.get(addCommand), sampleTask);
    }

    @Test
    public void nameDateTimeDescriptionTagVenuePriorityTaskTest() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) AddCommandParser.parse("CS2103 Lecture due/30/3 "
                + "dueT/16:00 d/Interesting #CS2103 @I3 p/3 \n");
        Name sampleTaskName = new Name("CS2103 Lecture");
        TaskDate sampleTaskDate = new TaskDate("30/3");
        TaskTime sampleTaskTime = new TaskTime("16:00");
        Description sampleTaskDescription = new Description("Interesting");
        Tag sampleTaskTag = new Tag("CS2103");
        Venue sampleTaskVenue = new Venue("I3");
        Priority sampleTaskPriority = new Priority("3");
        Task sampleTask = new Task(sampleTaskName, sampleTaskDate, sampleTaskTime,
                sampleTaskDescription, sampleTaskTag, sampleTaskVenue, sampleTaskPriority, false, false);
        assertEqualTasks((Task) field.get(addCommand), sampleTask);
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
}
