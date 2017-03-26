//@@author A0147996E
package guitests;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FinishCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ViewCommand;

import org.junit.Test;

public class HelpCommandTest extends TaskManagerGuiTest {
    @Test
    public void help() {
        //main commands help message display test
        assertHelpCommandSuccess("add");
        assertHelpCommandSuccess("delete");
        assertHelpCommandSuccess("edit");
        assertHelpCommandSuccess("find");
        assertHelpCommandSuccess("finish");
        assertHelpCommandSuccess("list");
        assertHelpCommandSuccess("view");
    }
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
        } else if (commandWord.equalsIgnoreCase("view")) {
            assertResultMessage(ViewCommand.MESSAGE_USAGE);
        }
    }
}
