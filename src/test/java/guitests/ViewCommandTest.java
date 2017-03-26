//@@ author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

public class ViewCommandTest extends TaskManagerGuiTest {

    @Test
    public void view () {
        //view all tasks in Dueue
        assertViewResult("view ", td.gym, td.gym2, td.gym3, td.date,
                td.cs2103, td.study, td.assignment);
        //view multiple tasks by view name
        assertViewResult("view personal", td.gym, td.gym2, td.gym3, td.date);
        //view single task by view name
        assertViewResult("view study", td.assignment);

        //view finished tasks in Dueue
        commandBox.runCommand("finish 1");
        commandBox.runCommand("finish 2");
        commandBox.runCommand("finish 3");
        assertViewResult("view finished", td.gym, td.gym2, td.gym3);

        //view favorite tasks in Dueue
        assertViewResult("view favorite", td.assignment);
    }

    @Test
    public void view_invalidCommand_fail() {
        commandBox.runCommand("views study");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("viewstudy");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertViewResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks found!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
