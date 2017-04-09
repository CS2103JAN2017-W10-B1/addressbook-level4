//@@author A0147996E
package guitests;

import static seedu.address.logic.commands.LoadCommand.COMMAND_WORD;
import static seedu.address.logic.commands.LoadCommand.MESSAGE_LOAD_SUCCESS;
import static seedu.address.logic.commands.LoadCommand.MESSAGE_LOAD_UNSUCCESS;

import org.junit.Test;

import seedu.address.logic.commands.CommandFormatter;

public class LoadCommandTest extends TaskManagerGuiTest {

    @Test
    public void load_LoadFromInternalDirectory_loadSuccess() {
        String path = "data/dueue.xml";
        commandBox.runCommand("load " + path);
        assertResultMessage(CommandFormatter.undoFormatter(
                String.format(MESSAGE_LOAD_SUCCESS, path), COMMAND_WORD));
    }

    @Test
    public void load_loadFromEmptyFile_loadUnsuccess() {
        String path = "data/doeNotExist.xml";
        commandBox.runCommand("load " + path);
        assertResultMessage(MESSAGE_LOAD_UNSUCCESS);
    }
}
