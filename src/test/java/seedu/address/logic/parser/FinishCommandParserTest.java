//@@author A0143409J

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.lang.reflect.Field;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.FinishCommand;
import seedu.address.logic.commands.IncorrectCommand;

public class FinishCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void finishTask_nullString_nullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        FinishCommandParser.parse(null);
    }

    @Test
    public void finishTask_validIndex_correctlyFinish() throws Exception {
        Field field = FinishCommand.class.getDeclaredField("targetIndex");
        field.setAccessible(true);

        FinishCommand finishCommand = (FinishCommand) FinishCommandParser.parse("1");
        assertEquals(field.get(finishCommand), 1);
    }

    @Test
    public void finishTask_invalidIndex_correctlyFinish() throws Exception {
        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand = (IncorrectCommand) FinishCommandParser.parse("+");
        assertEquals(field.get(incorrectCommand),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FinishCommand.MESSAGE_USAGE));
    }
}
