//@@author A0147996E
package guitests;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FinishCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.ScrollToCommand;
import seedu.address.logic.commands.ViewNextCommand;
import seedu.address.logic.commands.ViewOnCommand;

public class HelpCommandTest extends TaskManagerGuiTest {
    @Test
    public void help_getSpecificHelpMessage_success() {
        assertHelpCommandSuccess("add");
        assertHelpCommandSuccess("delete");
        assertHelpCommandSuccess("edit");
        assertHelpCommandSuccess("find");
        assertHelpCommandSuccess("finish");
        assertHelpCommandSuccess("list");
        assertHelpCommandSuccess("load");
        assertHelpCommandSuccess("scroll");
        assertHelpCommandSuccess("view");
    }

    /**
     * Checks whether the command message is correct.
     *
     * @param commandWord the command that user requests help from.
     */
    private void assertHelpCommandSuccess(String commandWord) {
        commandBox.runCommand("help " + commandWord);
        if (commandWord.equalsIgnoreCase("add")) {
            assertResultMessage(AddCommand.MESSAGE_USAGE);
        } else if (commandWord.equalsIgnoreCase("delete")) {
            assertResultMessage(DeleteCommand.MESSAGE_USAGE);
        } else if (commandWord.equalsIgnoreCase("edit")) {
            assertResultMessage(EditCommand.MESSAGE_USAGE);
        } else if (commandWord.equalsIgnoreCase("find")) {
            assertResultMessage(FindCommand.MESSAGE_USAGE);
        } else if (commandWord.equalsIgnoreCase("finish")) {
            assertResultMessage(FinishCommand.MESSAGE_USAGE);
        } else if (commandWord.equalsIgnoreCase("list")) {
            assertResultMessage(ListCommand.MESSAGE_USAGE);
        } else if (commandWord.equalsIgnoreCase("load")) {
            assertResultMessage(LoadCommand.MESSAGE_USAGE);
        } else if (commandWord.equalsIgnoreCase("scroll")) {
            assertResultMessage(ScrollToCommand.MESSAGE_USAGE);
        } else if (commandWord.equalsIgnoreCase("view")) {
            assertResultMessage(ViewNextCommand.MESSAGE_USAGE + "\n" +  ViewOnCommand.MESSAGE_USAGE);
        }
    }
}
