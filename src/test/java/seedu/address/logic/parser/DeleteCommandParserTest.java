//@@author A0143409J

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.IncorrectCommand;

public class DeleteCommandParserTest {

    public DeleteCommandParser theOne = DeleteCommandParser.getInstance();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
    }

    @Test
    public void deleteTask_emptyString_incorrectCommand() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) theOne.parse("");
        assertEquals(field.get(incorrectCommand),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void deleteTask_zerothIndex_incorrectCommand() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) theOne.parse("0\n");
        assertEquals(field.get(incorrectCommand),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
