//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandFormatter;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class RedoCommandTest extends TaskManagerGuiTest {

    @Test
    public void redo_undoCommands_redoSuccess() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();

        String commandToRedo = "add ";
        TestEvent eventToAdd = te.assignment;
        commandBox.runCommand(eventToAdd.getAddCommand());
        currentList = (TestUtil.addTasksToList(currentList, eventToAdd));
        assertRedoSuccess(currentList, commandToRedo);

        int targetIndex = 2;
        commandToRedo = "delete ";
        commandBox.runCommand(commandToRedo + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        assertRedoSuccess(currentList, commandToRedo);

        commandToRedo = "finish ";
        targetIndex = 2;
        commandBox.runCommand(commandToRedo + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        assertRedoSuccess(currentList, commandToRedo);

        commandToRedo = "edit ";
        targetIndex = 1;
        String detailsToEdit = " @Home";
        commandBox.runCommand(commandToRedo + targetIndex + detailsToEdit);
        currentList[targetIndex].setVenue("Home");
        assertRedoSuccess(currentList, commandToRedo);

        commandToRedo = "clear ";
        commandBox.runCommand(commandToRedo);
        currentList = new TestTask[] {};
        assertRedoSuccess(currentList, commandToRedo);
    }

    /**
     * Checks whether can correctly redo the previous command that has been undone.
     *
     * @param currentList the expected taskList after redo command.
     * @param commandWord the command word of the command to be redone.
     */
    private void assertRedoSuccess(final TestTask[] currentList, String commandWord) {
        commandBox.runCommand("undo");
        commandBox.runCommand("redo");

        assertTrue(taskListPanel.isListMatching(currentList));
        assertResultMessage(CommandFormatter.undoMessageFormatter(
                RedoCommand.MESSAGE_SUCCESS, commandWord + "command"));
    }
}
