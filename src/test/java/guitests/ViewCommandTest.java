//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ViewOnCommand.MESSAGE_NONNEGATIVE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;

public class ViewCommandTest extends TaskManagerGuiTest {

    @Test
    public void view_viewNextNDays_viewSuccess() {
        commandBox.runCommand(td.homework.getAddCommand());
        commandBox.runCommand(td.homework2.getAddCommand());
        assertViewResult("view next/1", td.homework, td.homework2);
        assertViewResult("view next/1000", td.homework, td.homework2, td.assignment,
            td.gym, td.gym2, td.gym3, td.cs2103, td.date, td.study);
    }

    @Test
    public void view_viewNextDate_viewSuccess() {
        assertViewResult("view next/01/01/2018", td.assignment, td.gym, td.gym2, td.gym3);
        assertViewResult("view next/01/01/2050", td.assignment,
            td.gym, td.gym2, td.gym3, td.cs2103, td.date, td.study);
    }
    @Test
    public void view_illegalParam_errorMessage() {
        commandBox.runCommand("view next/-1");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NONNEGATIVE));
        commandBox.runCommand("view on/01/04/2017");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NONNEGATIVE));
    }
    @Test
    public void view_viewOnNDays_viewSuccess() {
        commandBox.runCommand(td.homework.getAddCommand());
        commandBox.runCommand(td.homework2.getAddCommand());
        assertViewResult("view on/tmr",  td.homework, td.homework2);
    }
    @Test
    public void view_viewOnDate_viewSuccess() {
        assertViewResult("view on/20/12/2017", td.gym);
        assertViewResult("view on/01/02", td.cs2103);
    }

    @Test
    public void view_invalidCommand_unknownCommand() {
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
