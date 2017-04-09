//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {
    private TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void find_inNonEmptyList_findSuccess() {
        assertFindResult("find all birthday");
        assertFindResult("find unfinished CS2103", td.cs2103);
        assertFindResult("find gym cs2103", td.gym, td.gym2, td.gym3, td.cs2103);
    }

    @Test
    public void find_deletedTask_findFailure() {
        int targetIndex = 1;
        String  keyWord = currentList[targetIndex - 1].getName().toString();
        commandBox.runCommand("delete " + targetIndex);
        assertFindResult("find " + keyWord);
    }

    @Test
    public void find_finishedTask_findUnsucess() {
        int targetIndex = 3;
        String  keyWord = currentList[targetIndex - 1].getName().toString();
        commandBox.runCommand("finish " + targetIndex);
        assertFindResult("find unfinished" + keyWord);
    }

    @Test
    public void findEmptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find exercise");
    }

    @Test
    public void findInvalidCommandFail() {
        commandBox.runCommand("finds study");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Check if the tasks found matches the expected task list.
     *
     * @param command a string to be run as the test command.
     * @param expectedHits expected task lists after running {@code command}.
     */
    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(String.format(Command.getMessageForTaskFoundShownSummary(expectedHits.length)));
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
