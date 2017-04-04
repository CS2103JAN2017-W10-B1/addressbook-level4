//@@author A0147996E-reused
package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.testutil.TestTask;

public class ScrollToCommandTest extends TaskManagerGuiTest {
    private TestTask[] currentList = td.getTypicalTasks();

    @Test
    public void scroll_inNonEmptyList_scrollSuccess() {
        assertScrollSuccess(1);

        int taskCount = currentList.length;
        assertScrollSuccess(taskCount);
        int middleIndex = taskCount / 2;
        assertScrollSuccess(middleIndex);

        assertScrollInvalid(taskCount + 1);
        assertScrollSuccess(middleIndex);
    }

    @Test
    public void scroll_inEmptyList_scrollFailure() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertScrollInvalid(1);
    }

    private void assertScrollInvalid(int index) {
        commandBox.runCommand("scroll " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertScrollSuccess(int index) {
        commandBox.runCommand("scroll " + index);
        assertResultMessage("Scrolled to index " + index);
        assertEquals(currentList[index - 1].getAsText(), taskListPanel.getSelectedTasks().get(0).getAsText());
    }
}
