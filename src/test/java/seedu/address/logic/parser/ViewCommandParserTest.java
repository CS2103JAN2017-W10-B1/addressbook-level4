//@@author A0143409J

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.lang.reflect.Field;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ViewNextCommand;
import seedu.address.logic.commands.ViewOnCommand;

public class ViewCommandParserTest {

    public ViewCommandParser theOne = ViewCommandParser.getInstance();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void viewParse_emptyString_viewToday() throws Exception {
        Field field = ViewNextCommand.class.getDeclaredField("numberOfDays");
        field.setAccessible(true);

        ViewNextCommand viewCommand =  (ViewNextCommand) theOne.parse("");

        assertEquals(field.get(viewCommand), "0");
    }

    @Test
    public void viewParse_nextValid_viewNextCommand() throws Exception {

        Field field = ViewNextCommand.class.getDeclaredField("numberOfDays");
        field.setAccessible(true);

        ViewNextCommand viewCommand =  (ViewNextCommand) theOne.parse("next/10");

        assertEquals(field.get(viewCommand), "10");
    }

    @Test
    public void viewParse_nextInvalid_incorrectCommand() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) theOne.parse("next/abc");

        assertEquals(field.get(incorrectCommand), String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewNextCommand.MESSAGE_USAGE));
    }

    @Test
    public void viewParse_onValid_viewOnCommand() throws Exception {

        Field field = ViewOnCommand.class.getDeclaredField("numberOfDays");
        field.setAccessible(true);

        ViewOnCommand viewOnCommand =  (ViewOnCommand) theOne.parse("on/10");

        assertEquals(field.get(viewOnCommand), "10");
    }

    @Test
    public void viewParse_onInvalid_incorrectCommand() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) theOne.parse("on/abc");

        assertEquals(field.get(incorrectCommand), String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewOnCommand.MESSAGE_USAGE));
    }
}
