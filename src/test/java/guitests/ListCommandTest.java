//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ListCommand.MESSAGE_LIST_DOES_NOT_EXIST;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

public class ListCommandTest extends TaskManagerGuiTest {

    @Test
    public void list_byListNamesFavouriteAll_listSuccess() {
        assertListResult("list all", td.assignment, td.gym, td.gym2, td.gym3, td.cs2103,
                td.date, td.study);
        assertListResult("list personal", td.gym, td.gym2, td.gym3, td.date);
        assertListResult("list all personal", td.gym, td.gym2, td.gym3, td.date);
        assertListResult("list study", td.assignment);
        assertListResult("list favorite", td.assignment, td.gym, td.cs2103, td.study);
        assertListResult("list favorite study", td.assignment);
        assertListResult("list favorite personal", td.gym);
    }

    @Test
    public void list_finishedTasks_listSuccess() {
        commandBox.runCommand("list all");
        commandBox.runCommand("finish 1");
        td.assignment.setFinished(true);
        assertListResult("list finished", td.assignment);
        commandBox.runCommand("list");
        commandBox.runCommand("finish 1");
        commandBox.runCommand("finish 1");
        commandBox.runCommand("finish 1");
        td.gym.setFinished(true);
        td.gym2.setFinished(true);
        td.gym3.setFinished(true);
        assertListResult("list finished personal", td.gym, td.gym2, td.gym3);
    }

    @Test
    public void list_invalidCommandFormat_unknownCommand() {
        commandBox.runCommand("lists study");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        commandBox.runCommand("liststudy");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void list_invalidListname_noTasksListed() {
        commandBox.runCommand("clear");
        assertListResult("list exercise");
        assertResultMessage(MESSAGE_LIST_DOES_NOT_EXIST);
        assertListResult("list all exercise");
        assertResultMessage(MESSAGE_LIST_DOES_NOT_EXIST);
        assertListResult("list favorite exercise");
        assertResultMessage(MESSAGE_LIST_DOES_NOT_EXIST);
        assertListResult("list finished exercise");
        assertResultMessage(MESSAGE_LIST_DOES_NOT_EXIST);
    }

    /**
     * Checks whether the list command has the correct matched tasks.
     *
     * @param command list command to be tested as a string.
     * @param expectedHits expected test tasks to be listed.
     */
    private void assertListResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
