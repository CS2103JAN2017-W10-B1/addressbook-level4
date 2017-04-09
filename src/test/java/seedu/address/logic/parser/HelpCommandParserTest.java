//@@author A0143409J

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FinishCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;

public class HelpCommandParserTest {

    public HelpCommandParser theOne = HelpCommandParser.getInstance();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void helpMessage_nullString_general() throws Exception {

        Field field = HelpCommand.class.getDeclaredField("usageMessage");
        field.setAccessible(true);

        HelpCommand helpCommand =  (HelpCommand) theOne.parse(null);
        assertEquals(field.get(helpCommand), null);
    }

    @Test
    public void helpMessage_correctCommand_correctlyDisplayed() throws Exception {

        Field field = HelpCommand.class.getDeclaredField("usageMessage");
        field.setAccessible(true);

        HelpCommand helpCommandAdd =  (HelpCommand) theOne.parse(AddCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandAdd), AddCommand.MESSAGE_USAGE);

        HelpCommand helpCommandDelete =  (HelpCommand) theOne.parse(DeleteCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandDelete), DeleteCommand.MESSAGE_USAGE);

        HelpCommand helpCommandExit =  (HelpCommand) theOne.parse(ExitCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandExit), ExitCommand.MESSAGE_USAGE);

        HelpCommand helpCommandHelp =  (HelpCommand) theOne.parse(HelpCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandHelp), HelpCommand.MESSAGE_USAGE);

        HelpCommand helpCommandClear =  (HelpCommand) theOne.parse(ClearCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandClear), ClearCommand.MESSAGE_USAGE);

        HelpCommand helpCommandEdit =  (HelpCommand) theOne.parse(EditCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandEdit), EditCommand.MESSAGE_USAGE);

        HelpCommand helpCommandFinish =  (HelpCommand) theOne.parse(FinishCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandFinish), FinishCommand.MESSAGE_USAGE);

        HelpCommand helpCommandFind =  (HelpCommand) theOne.parse(FindCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandFind), FindCommand.MESSAGE_USAGE);

        HelpCommand helpCommandList =  (HelpCommand) theOne.parse(ListCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandList), ListCommand.MESSAGE_USAGE);
    }
}
