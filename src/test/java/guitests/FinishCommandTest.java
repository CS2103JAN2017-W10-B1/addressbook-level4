//@@ author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.FinishCommand.MESSAGE_FINISH_TASK_MARKED;
import static seedu.address.logic.commands.FinishCommand.MESSAGE_FINISH_TASK_SUCCESS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class FinishCommandTest extends TaskManagerGuiTest {
    @Test
    public void finishValidTest() {
        //add some events in first
        TestTask[] currentList = td.getTypicalTasks();
        currentList = TestUtil.addEventsToList(currentList, te.assignment, te.gym);
        TestEvent eventToAdd = te.assignment;
        commandBox.runCommand(eventToAdd.getAddCommand());
        eventToAdd = te.gym;
        commandBox.runCommand(eventToAdd.getAddCommand());

        //finish unfinished task in list all
        int targetIndex = 1;
        assertFinishSuccess(targetIndex, currentList);

        //finish tasks or events under a specific list
        commandBox.runCommand("list personal");
        targetIndex = 1;
        assertFinishSuccess(targetIndex, new TestTask[] {td.gym, te.gym, td.gym2, td.gym3, td.date});
        targetIndex = 2;
        assertFinishSuccess(targetIndex, new TestTask[] {te.gym, td.gym2, td.gym3, td.date});
    }

    @Test
    public void finishInvalidTest() {
        //cannot finish task that has already been marked as finished
        int targetIndex = 1;
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("finish " + targetIndex);
        commandBox.runCommand("list all");
        commandBox.runCommand("finish " + targetIndex);
        assertResultMessage(MESSAGE_FINISH_TASK_MARKED);

        //invalid command, index must be positive integer and must not exceed current list length
        int maxIndex = currentList.length;
        commandBox.runCommand("finish " + (maxIndex + 2));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //invalid command, command word must be valid
        commandBox.runCommand("finishes " + targetIndex);
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Runs the finish command to finish the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. index 1 to finish the first task in the list,
     * @param currentList A copy of the current unfinished list of tasks (before finish command).
     */
    private void assertFinishSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToFinish = currentList[targetIndexOneIndexed - 1]; // -1 as array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
        commandBox.runCommand("finish " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the finished task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_FINISH_TASK_SUCCESS, taskToFinish.toString() + " finished"));
    }
}
