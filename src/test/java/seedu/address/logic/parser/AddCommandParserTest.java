package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.lang.reflect.Field;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Date;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;
import seedu.address.model.task.Time;
import seedu.address.model.task.Venue;

public class AddCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void nullTest() throws Exception{

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) AddCommandParser.parse(null);
        assertEquals(incorrectCommand.feedbackToUser, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void nameOnlyTaskTest() throws Exception {

        Field field = AddCommand.class.getDeclaredField("toAdd");
        field.setAccessible(true);

        AddCommand addCommand =  (AddCommand) AddCommandParser.parse("CS2103 Lecture \n");
        Name sampleTask1Name = new Name("CS2103 Lecture"); 
        Task sampleTask1 = new Task(sampleTask1Name, new Date(""), new Time(""), new Description(""), new Tag(""), new Venue(""), new Priority(""), false, false);
        assertEqualTasks(addCommand, sampleTask1);
    }

	private void assertEqualTasks(AddCommand addCommand, Task sampleTask) {
		assertEquals(addCommand.getTask().getName(), sampleTask.getName());
        assertEquals(addCommand.getTask().getDate(), sampleTask.getDate());
        assertEquals(addCommand.getTask().getTime(), sampleTask.getTime());
        assertEquals(addCommand.getTask().getDescription(), sampleTask.getDescription());
        assertEquals(addCommand.getTask().getTag(), sampleTask.getTag());
        assertEquals(addCommand.getTask().getVenue(), sampleTask.getVenue());
        assertEquals(addCommand.getTask().getPriority(), sampleTask.getPriority());
        assertEquals(addCommand.getTask().isFavorite(), sampleTask.isFavorite());
        assertEquals(addCommand.getTask().isFinished(), sampleTask.isFinished());
	}
}
