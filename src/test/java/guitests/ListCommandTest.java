//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

public class ListCommandTest extends TaskManagerGuiTest {

    @Test
    public void list_nonEmptyList() {
        //list all tasks in Dueue
        assertListResult("list all", td.assignment, td.gym, td.gym2, td.gym3, td.cs2103,
                td.date, td.study);
        //list multiple tasks by list name
        assertListResult("list personal", td.gym, td.gym2, td.gym3, td.date);
        //list single task by list name
        assertListResult("list study", td.assignment);
        //list favorite tasks in Dueue
        assertListResult("list favorite", td.assignment, td.gym, td.cs2103, td.study);
        //TODO: uncomment tests below when list command is finalized
        //list favorite tasks in study
        assertListResult("list favorite study", td.assignment);
        //list favorite tasks in personal
        assertListResult("list favorite personal", td.gym);
    }
    
    @Test
    public void list_finishedList () {
        commandBox.runCommand("list all");
        commandBox.runCommand("finish 1"); //finish assignment
        td.assignment.setFinished(true);
        assertListResult("list finished", td.assignment);
        commandBox.runCommand("list");
        commandBox.runCommand("finish 1");
        commandBox.runCommand("finish 1");
        commandBox.runCommand("finish 1");//finish gym, gym2, gym3
        td.gym.setFinished(true);
        td.gym2.setFinished(true);
        td.gym3.setFinished(true);
        assertListResult("list finished personal", td.gym, td.gym2, td.gym3);
    }

    @Test
    public void list_invalidCommand_fail() {
        commandBox.runCommand("lists study");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("liststudy");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void list_emptyList() {
        commandBox.runCommand("clear");
        assertListResult("list exercise"); // no results
    }

    private void assertListResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
