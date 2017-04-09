//@@author A0143409J

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.lang.reflect.Field;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ScrollToCommand;

public class ScrollToCommandParserTest {

    public ScrollToCommandParser theOne = ScrollToCommandParser.getInstance();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void scrollToCommand_nullString_assertionError() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        thrown.expect(AssertionError.class);
        theOne.parse(null);
    }

    @Test
    public void scrollToCommand_emptyString_incorrectCommand() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) theOne.parse("");
        assertEquals(field.get(incorrectCommand), String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ScrollToCommand.MESSAGE_USAGE));
    }

    @Test
    public void scrollToCommand_oneDigitString_correctConversion() throws Exception {

        Field field = ScrollToCommand.class.getDeclaredField("targetIndex");
        field.setAccessible(true);

        ScrollToCommand loadCommand =  (ScrollToCommand) theOne.parse("1");
        assertEquals(field.get(loadCommand), 1);
    }
}
