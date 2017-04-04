//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.UndoCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNSUCCESS;

import org.junit.Test;

import seedu.address.logic.commands.CommandFormatter;
import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class UndoCommandTest extends TaskManagerGuiTest {
    private TestTask[] currentList = td.getTypicalTasks();
    private String commandToUndo;
    private String commandWord;

    @Test
    public void undo_ableUndoCommands_undoSuccess() {
        TestEvent eventToAdd = te.assignment;
        commandToUndo = eventToAdd.getAddCommand();
        commandWord = "add";
        assertUndoSuccess(currentList, commandToUndo, commandWord);

        commandToUndo = "delete 1";
        commandWord = "delete";
        assertUndoSuccess(currentList, commandToUndo, commandWord);

        commandToUndo = "finish 1";
        commandWord = "finish";
        assertUndoSuccess(currentList, commandToUndo, commandWord);

        commandToUndo = "load data/dueue.xml";
        commandWord = "load";
        assertUndoSuccess(currentList, commandToUndo, commandWord);

        String detailsToEdit = " n/assignment2 dueT/18:30";
        commandToUndo = "edit 2 " + detailsToEdit;
        commandWord = "edit";
        assertUndoSuccess(currentList, commandToUndo, commandWord);
    }
    @Test
    public void undo_noMoreCommandToUndo_undoUnsuccess() {
        commandBox.runCommand("list all"); //list is not ableUndoCommand
        commandBox.runCommand("undo");
        assertResultMessage(MESSAGE_UNSUCCESS);
    }

    @Test
    public void undo_stackOfAbleUndoCommands_undoSuccess() {
        commandBox.runCommand("finish 1");
        TestTask finishedTask = currentList[0];
        currentList = TestUtil.removeTaskFromList(currentList, 1);

        assertUndoSuccess(currentList, "clear", "clear");
        currentList = TestUtil.addTasksToList(currentList, finishedTask);

        commandWord = "finish";
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));
        assertResultMessage(CommandFormatter.undoMessageFormatter(MESSAGE_SUCCESS,
                (commandWord + " command")));
    }

    private void assertUndoSuccess(final TestTask[] currentList,
            String commandToUndo, String commandWord) {
        commandBox.runCommand(commandToUndo);
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));
        assertResultMessage(CommandFormatter.undoMessageFormatter(MESSAGE_SUCCESS,
                (commandWord + " command")));
    }
}
