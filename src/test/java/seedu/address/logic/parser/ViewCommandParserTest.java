//@@ author A0143409J

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.lang.reflect.Field;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewOnCommand;

public class ViewCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void nullTest() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) ViewCommandParser.parse(null);
        assertEquals(field.get(incorrectCommand), String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void viewNextValidIntTest() throws Exception {

        Field field = ViewCommand.class.getDeclaredField("numberOfDays");
        field.setAccessible(true);

        ViewCommand viewCommand =  (ViewCommand) ViewCommandParser.parse("next/10");

        assertEquals(field.get(viewCommand), "10");
    }

    @Test
    public void viewNextInvalidIntTest() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) ViewCommandParser.parse("next/abc");

        assertEquals(field.get(incorrectCommand), String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void viewOnValidIntTest() throws Exception {

        Field field = ViewOnCommand.class.getDeclaredField("numberOfDays");
        field.setAccessible(true);

        ViewOnCommand viewOnCommand =  (ViewOnCommand) ViewCommandParser.parse("on/10");

        assertEquals(field.get(viewOnCommand), "10");
    }

    @Test
    public void viewOnInvalidIntTest() throws Exception {

        Field field = IncorrectCommand.class.getDeclaredField("feedbackToUser");
        field.setAccessible(true);

        IncorrectCommand incorrectCommand =  (IncorrectCommand) ViewCommandParser.parse("on/abc");

        assertEquals(field.get(incorrectCommand), String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void viewFormatterTest() throws Exception {

        String[] parameters =  ViewCommandParser.formatter("next/10");

        assertEquals(parameters[0], "next");
        assertEquals(parameters[1], "10");
    }
}
