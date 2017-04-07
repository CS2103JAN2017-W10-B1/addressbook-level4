//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.FinishCommand.MESSAGE_FINISH_TASK_MARKED;
import static seedu.address.logic.commands.FinishCommand.MESSAGE_FINISH_TASK_SUCCESS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class FinishCommandTest extends TaskManagerGuiTest {
    private TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void finish_recurringTask_finishSuccess() throws IllegalValueException {
        commandBox.runCommand("clear");
        TestTask[] currentList = {};
        int targetIndex = 1;
        currentList = TestUtil.addTasksToList(currentList, tr.gym);
        commandBox.runCommand(tr.gym.getAddCommand());

        currentList = TestUtil.addTasksToList(currentList, tr.cs2103);
        commandBox.runCommand(tr.cs2103.getAddCommand());

        commandBox.runCommand("finish " + targetIndex);
        currentList[targetIndex - 1].setDate("27/12/2017");

        targetIndex = 2;
        commandBox.runCommand("finish " + targetIndex);
        currentList[targetIndex - 1].setDate("01/03/2018");
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void finish_validIndexTest_finishSuccess() {
        int targetIndex = 1;
        assertFinishSuccess(targetIndex);
        targetIndex = currentList.length / 2;
        assertFinishSuccess(targetIndex);
        targetIndex = currentList.length;
        assertFinishSuccess(targetIndex);
    }

    @Test
    public void finish_alreadyFinishedTask_finishUnsuccess() {
        int targetIndex = 1;
        commandBox.runCommand("finish " + targetIndex);
        commandBox.runCommand("list all");
        commandBox.runCommand("finish " + targetIndex);
        assertResultMessage(MESSAGE_FINISH_TASK_MARKED);
    }

    @Test
    public void finish_outOfBoundInx_finishUnsuccess() {
        int maxIndex = currentList.length;
        commandBox.runCommand("finish " + (maxIndex + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void finish_invalidCommandFormat_unknownCommand() {
        commandBox.runCommand("finishes 1");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Runs the finish command to finish the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to finish the first task in the list,
     * @param currentList A copy of the current unfinished list of tasks (before finish command).
     */
    private void assertFinishSuccess(int targetIndexOneIndexed) {
        TestTask taskToFinish = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        currentList = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("finish " + targetIndexOneIndexed);
        assertResultMessage(String.format(MESSAGE_FINISH_TASK_SUCCESS, taskToFinish.getName()));
        commandBox.runCommand("list");
        assertTrue(taskListPanel.isListMatching(currentList));
    }
}
