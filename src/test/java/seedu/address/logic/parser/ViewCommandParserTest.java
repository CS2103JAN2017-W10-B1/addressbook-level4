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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void viewParse_nullString_incorrectCommand() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) ViewCommandParser.parse(null);
        assertEquals(field.get(incorrectCommand), String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewOnCommand.MESSAGE_USAGE  + ViewNextCommand.MESSAGE_USAGE));
    }

    @Test
    public void viewParse_nextValid_viewNextCommand() throws Exception {

        Field field = ViewNextCommand.class.getDeclaredField("numberOfDays");
        field.setAccessible(true);

        ViewNextCommand viewCommand =  (ViewNextCommand) ViewCommandParser.parse("next/10");

        assertEquals(field.get(viewCommand), "10");
    }

    @Test
    public void viewParse_nextInvalid_incorrectCommand() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) ViewCommandParser.parse("next/abc");

        assertEquals(field.get(incorrectCommand), String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewNextCommand.MESSAGE_USAGE));
    }

    @Test
    public void viewParse_onValid_viewOnCommand() throws Exception {

        Field field = ViewOnCommand.class.getDeclaredField("numberOfDays");
        field.setAccessible(true);

        ViewOnCommand viewOnCommand =  (ViewOnCommand) ViewCommandParser.parse("on/10");

        assertEquals(field.get(viewOnCommand), "10");
    }

    @Test
    public void viewParse_onInvalid_incorrectCommand() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) ViewCommandParser.parse("on/abc");

        assertEquals(field.get(incorrectCommand), String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewOnCommand.MESSAGE_USAGE));
    }

    @Test
    public void viewFormatter_validString_successfulFormatting() throws Exception {

        String[] parameters =  ViewCommandParser.formatter("next/10");

        assertEquals(parameters[0], "next");
        assertEquals(parameters[1], "10");
    }
}
