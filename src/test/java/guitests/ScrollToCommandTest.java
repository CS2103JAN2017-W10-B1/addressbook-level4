//@@author A0147996E-reused
package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.task.ReadOnlyTask;

public class ScrollToCommandTest extends TaskManagerGuiTest {


    @Test
    public void scrolltaskNonEmptyList() {

        assertSelectionInvalid(10); // invalid index
        assertNotaskSelected();

        assertSelectionSuccess(1); // first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); // a task in the middle of the list

        assertSelectionInvalid(taskCount + 1); // invalid index
        assertScrollSucess(middleIndex);

        /* Testing other invalid indexes such as -1 should be done when testing the ScrollToCommand */
    }

    @Test
    public void scrolltaskEmptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("scroll " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("scroll " + index);
        assertResultMessage("Scrolled to index " + index);
        assertScrollSucess(index);
    }

    private void assertScrollSucess(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask scrolledtasks = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index - 1), scrolledtasks);
    }

    private void assertNotaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }
}
