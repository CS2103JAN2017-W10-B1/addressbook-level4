//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.UndoCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_UNSUCCESS;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandFormatter;
import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestRecurringTask;
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

        commandToUndo = "clear";
        commandWord = "clear";
        assertUndoSuccess(currentList, commandToUndo, commandWord);

        commandToUndo = "load data/dueue.xml";
        commandWord = "load";
        assertUndoSuccess(currentList, commandToUndo, commandWord);

        String detailsToEdit = "n/assignment2 dueT/18:30";
        commandToUndo = "edit 2 " + detailsToEdit;
        commandWord = "edit";
        assertUndoSuccess(currentList, commandToUndo, commandWord);
    }

    @Test
    public void undo_editNextRecurringTask_undoSuccess() throws IllegalValueException {
        TestRecurringTask recurringTaskToAdd = tr.gym;
        commandBox.runCommand("clear");
        commandBox.runCommand(recurringTaskToAdd.getAddCommand());

        String detailsToEdit = "d/test";
        commandBox.runCommand("edit " + 1 + " once/t " + detailsToEdit);
        TestTask typicalTaskToAdd = new TestTask(tr.gym);
        typicalTaskToAdd.setDescription("test");
        recurringTaskToAdd.setDate("27/12/2017");

        // confirm the new typical and recurring task is added to the current list
        assertTrue(taskListPanel.navigateToTask(typicalTaskToAdd));
        assertTrue(taskListPanel.navigateToTask(recurringTaskToAdd));

        // confirm the list now contains no task except the Tasks with updated details
        TestTask[] expectedTasksList = new TestTask[2];
        expectedTasksList[0] = typicalTaskToAdd;
        expectedTasksList[1] = recurringTaskToAdd;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));

        //execute undo and confirm the list is reverted back to the original
        commandBox.runCommand("undo");
        expectedTasksList = new TestTask[1];
        expectedTasksList[0] = tr.gym;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
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

    /**
     * Checks whether the previous command has been correctly undone.
     *
     * @param currentList expected list after undo command is executed.
     * @param commandToUndo command as a string to be undone.
     * @param commandWord keyword of the command to undo.
     */
    private void assertUndoSuccess(final TestTask[] currentList,
            String commandToUndo, String commandWord) {
        commandBox.runCommand(commandToUndo);
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isListMatching(currentList));
        assertResultMessage(CommandFormatter.undoMessageFormatter(MESSAGE_SUCCESS,
                (commandWord + " command")));
    }
}
