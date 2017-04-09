//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.logic.commands.DeleteCommand.COMMAND_DELETE;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import org.junit.Test;

import seedu.address.logic.commands.CommandFormatter;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class DeleteCommandTest extends TaskManagerGuiTest {

    private TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void delete_validIndexGiven_deleteSucess() {
        int targetIndex = 1;
        System.out.println("啊 啊啊啊啊" + currentList[targetIndex - 1].toString());
        assertDeleteSuccess(targetIndex, currentList);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);

        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);

        targetIndex = currentList.length / 2;
        assertDeleteSuccess(targetIndex, currentList);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
    }

    @Test
    public void delete_invalidIndexGiven_deleteFailure() {
        commandBox.runCommand("delete " + (currentList.length + 1));
        assertResultMessage(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     *
     * @param targetIndex e.g. index 1 to delete the first task in the list,
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndex, final TestTask[] currentList) {
        TestTask taskToDelete = currentList[targetIndex - 1]; // -1 as array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndex);

        commandBox.runCommand("delete " + targetIndex);
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        assertResultMessage(CommandFormatter.undoFormatter(
                String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete), COMMAND_DELETE));
    }
}
