//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.RedoCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class RedoCommandTest extends TaskManagerGuiTest {
    @Test
    public void redo() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();

        //redo add event
        TestEvent eventToAdd = te.assignment;
        commandBox.runCommand(eventToAdd.getAddCommand());
        currentList = (TestUtil.addEventsToList(currentList, eventToAdd));
        //assertRedoSuccess(currentList);

        //redo add task
        TestTask taskToAdd = td.travel;
        commandBox.runCommand(taskToAdd.getAddCommand());
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        //assertRedoSuccess(currentList);

        //redo delete command
        int targetIndex = 2;
        commandBox.runCommand("delete " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        //assertRedoSuccess(currentList);

        targetIndex = 3;
        commandBox.runCommand("delete " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        //assertRedoSuccess(currentList);

        //redo finish command
        targetIndex = 2;
        commandBox.runCommand("finish " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        //assertRedoSuccess(currentList);

        targetIndex = 3;
        commandBox.runCommand("finish " + targetIndex);
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        assertRedoSuccess(currentList);

        //redo editing command
        targetIndex = 1;
        String detailsToEdit = " @Home";
        commandBox.runCommand("edit " + targetIndex + detailsToEdit);
        currentList[targetIndex].setVenue("Home");
        assertRedoSuccess(currentList);

        targetIndex = 2;
        detailsToEdit = " d/There are so many tests";
        commandBox.runCommand("edit " + targetIndex + detailsToEdit);
        currentList[targetIndex].setDescription("There are so many tests");;
        assertRedoSuccess(currentList);
    }

    private void assertRedoSuccess(final TestTask[] currentList) {
        commandBox.runCommand("undo");
        commandBox.runCommand("redo");
        //confirm the list now contains all previous tasks
        assertTrue(taskListPanel.isListMatching(currentList));
        //confirm the result message is correct
        assertResultMessage(MESSAGE_SUCCESS);
    }
}
