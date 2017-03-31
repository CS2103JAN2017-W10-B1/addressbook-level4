//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.UndoCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestTask;

public class UndoCommandTest extends TaskManagerGuiTest {

    @Test
    public void undo_add() {
        TestTask[] currentList = td.getTypicalTasks();

        TestEvent eventToAdd = te.assignment;
        commandBox.runCommand(eventToAdd.getAddCommand());
        assertUndoSuccess(currentList);

        TestTask taskToAdd = td.travel;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertUndoSuccess(currentList);
    }

    @Test
    public void undo_delete() {
        TestTask[] currentList = td.getTypicalTasks();

        int targetIndex = 2;
        commandBox.runCommand("delete " + targetIndex);
        assertUndoSuccess(currentList);

        targetIndex = 3;
        commandBox.runCommand("delete " + targetIndex);
        assertUndoSuccess(currentList);
    }

    @Test
    public void undo_finish() {
        TestTask[] currentList = td.getTypicalTasks();

        int targetIndex = 2;
        commandBox.runCommand("finish " + targetIndex);
        assertUndoSuccess(currentList);

        targetIndex = 3;
        commandBox.runCommand("finish " + targetIndex);
        assertUndoSuccess(currentList);
    }

    @Test
    public void undo_edit() {
        TestTask[] currentList = td.getTypicalTasks();

        int targetIndex = 2;
        String detailsToEdit = " n/assignment2 dueT/18:30";
        commandBox.runCommand("edit " + targetIndex + detailsToEdit);
        assertUndoSuccess(currentList);

        targetIndex = 4;
        detailsToEdit = " start/03/05/ due/08/05 *u p/important #school";
        commandBox.runCommand("edit " + targetIndex + detailsToEdit);
        assertUndoSuccess(currentList);
    }

    private void assertUndoSuccess(final TestTask[] currentList) {
        commandBox.runCommand("undo");
        //confirm the list now contains all previous tasks
        assertTrue(taskListPanel.isListMatching(currentList));
        //confirm the result message is correct
        assertResultMessage(MESSAGE_SUCCESS);
    }
}
