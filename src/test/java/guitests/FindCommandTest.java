//@@ author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void findNonEmptyList() {
        assertFindResult("find birthday"); // no results
        assertFindResult("find CS2103", td.cs2103); //find command is case insensitive
        // multiple results with duplicate task names but different taskStates
        assertFindResult("find gym", td.gym, td.gym2, td.gym3);

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find assignment", td.assignment);
    }

    @Test
    public void findEmptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find exercise"); // no results
    }

    @Test
    public void findInvalidCommand_fail() {
        commandBox.runCommand("finds study");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("findstudy");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks found!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
