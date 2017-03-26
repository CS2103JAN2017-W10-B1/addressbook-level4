//@@ author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

public class ViewCommandTest extends TaskManagerGuiTest {

    @Test
    public void view () {
        assertViewResult("view next/270",  td.assignment, td.gym, td.gym2, td.gym3);
        assertViewResult("view next/1");
        assertViewResult("view next/1000", td.assignment, td.gym, td.gym2, td.gym3,
                td.cs2103, td.date, td.study);
    }

    @Test
    public void view_invalidCommand_fail() {
        commandBox.runCommand("views next/10");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("viewnext /10");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertViewResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
