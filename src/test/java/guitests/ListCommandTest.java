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
        assertListResult("list all", td.gym, td.gym2, td.gym3, td.date, 
                td.cs2103, td.study, td.assignment); 
        //list multiple tasks by list name
        assertListResult("list personal", td.gym, td.gym2, td.gym3, td.date);
        //list single task by list name
        assertListResult("list study", td.assignment);

        //list finished tasks in Dueue
        commandBox.runCommand("finish 1");
        commandBox.runCommand("finish 2");
        commandBox.runCommand("finish 3");
        assertListResult("list finished", td.gym, td.gym2, td.gym3);
        
        //list favorite tasks in Dueue
        assertListResult("list favorite", td.assignment);
        
    }

    @Test
    public void list_emptyList() {
        commandBox.runCommand("clear");
        assertListResult("list exercise"); // no results
    }

    @Test
    public void list_invalidCommand_fail() {
        commandBox.runCommand("lists study");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("liststudy");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertListResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks found!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
