//@@author A0143049J

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.lang.reflect.Field;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Date;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Time;
import seedu.address.model.task.Venue;

public class DeleteCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
    }

    @Test
    public void nullTest() throws Exception{

        Field field = DeleteCommand.class.getDeclaredField("targetIndex");
        field.setAccessible(true);

        DeleteCommand deleteCommand =  (DeleteCommand) DeleteCommandParser.parse(null);
        assertEquals(field.get(deleteCommand), null);
    }

    @Test
    public void zeroTargetIndexTest() throws Exception {

        Field field = DeleteCommand.class.getDeclaredField("targetIndex");
        field.setAccessible(true);

        Name sampleName = new Name("CS2103 Lecture");
        Optional<Name> optionalName = Optional.of(sampleName);
        editTaskDescriptor.setName(optionalName);
        DeleteCommand editCommand =  (DeleteCommand) DeleteCommandParser.parse("1 n/CS2103 Lecture \n");
        assertEqualEditTaskDescriptor((EditTaskDescriptor) field.get(editCommand), editTaskDescriptor);
    }

	private void assertEqualEditTaskDescriptor(EditTaskDescriptor descriptor1, EditTaskDescriptor descriptor2) {
		assertEquals(descriptor1.getName(), descriptor2.getName());
        assertEquals(descriptor1.getDate(), descriptor2.getDate());
        assertEquals(descriptor1.getTime(), descriptor2.getTime());
        assertEquals(descriptor1.getDescription(), descriptor2.getDescription());
        assertEquals(descriptor1.getTag(), descriptor2.getTag());
        assertEquals(descriptor1.getVenue(), descriptor2.getVenue());
        assertEquals(descriptor1.getPriority(), descriptor2.getPriority());
	}
}
