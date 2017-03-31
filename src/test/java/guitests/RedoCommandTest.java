//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.RedoCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class RedoCommandTest extends TaskManagerGuiTest {

    @Test
    public void redo_add() {
        TestTask[] currentList = td.getTypicalTasks();

        TestEvent eventToAdd = te.assignment;
        commandBox.runCommand(eventToAdd.getAddCommand());
        currentList = (TestUtil.addEventsToList(currentList, eventToAdd));
        assertRedoSuccess(currentList);

        TestTask taskToAdd = td.travel;
        commandBox.runCommand(taskToAdd.getAddCommand());
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertRedoSuccess(currentList);
    }

    @Test
    public void redo_delete() {
        TestTask[] currentList = td.getTypicalTasks();

        int targetIndex = 2;
        commandBox.runCommand("delete " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        assertRedoSuccess(currentList);

        targetIndex = 3;
        commandBox.runCommand("delete " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        assertRedoSuccess(currentList);
    }
/*
    @Test
    public void redo_finish() {
        TestTask[] currentList = td.getTypicalTasks();

        int targetIndex = 2;
        commandBox.runCommand("finish " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        assertRedoSuccess(currentList);

        targetIndex = 3;
        commandBox.runCommand("finish " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        assertRedoSuccess(currentList);
    }

    @Test
    public void redo_edit() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();

        int targetIndex = 1;
        String detailsToEdit = " n/assignment2 dueT/18:30";
        commandBox.runCommand("edit " + targetIndex + detailsToEdit);

        //ensure the editedTask is inserted at the right place as sorted
        TestTask editedTask = new TestTask(currentList[targetIndex]);
        editedTask.setName("assignment2");
        editedTask.setTime("18:30");
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        currentList = TestUtil.addTasksToList(currentList, editedTask);
        assertRedoSuccess(currentList);

        targetIndex = 4;
        detailsToEdit = " start/03/05/ due/08/05 *u p/important #school";
        commandBox.runCommand("edit " + targetIndex + detailsToEdit);

        editedTask = new TestTask(currentList[targetIndex]);
        editedTask.setDate("08/05");
        editedTask.setFavorite(false);
        editedTask.setTag("school");
        ((TestEvent) editedTask).setStartDate("03/05");
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        currentList = TestUtil.addTasksToList(currentList, editedTask);

        assertRedoSuccess(currentList);
    }*/

    private void assertRedoSuccess(final TestTask[] currentList) {
        commandBox.runCommand("undo");
        commandBox.runCommand("redo");
        //confirm the list now contains all previous tasks
        assertTrue(taskListPanel.isListMatching(currentList));
        //confirm the result message is correct
        assertResultMessage(MESSAGE_SUCCESS);
    }
}
