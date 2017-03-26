//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //gym, gym2, gym3, cs2103, study, assignment, date
        TestTask[] currentList = {};
        commandBox.runCommand("clear");

        //TODO: add a floating task with name only
        TestTask taskToAdd = td.shopping2;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add a task with other fields other than name
        taskToAdd = td.date;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //try to add a duplicate task
        commandBox.runCommand(td.date.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add a task with duplicate name with existing unfinished task under different lists
        taskToAdd = td.date2;
        commandBox.runCommand(td.date2.getAddCommand());
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add a task with duplicate name and list but different date
        taskToAdd = td.date3;
        commandBox.runCommand(td.date3.getAddCommand());
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add a task with duplicate name, list, date but different time
        taskToAdd = td.date4;
        commandBox.runCommand(td.date4.getAddCommand());
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.assignment);

        //unknown command
        commandBox.runCommand("adds homework");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        //invalid command, task must have a name
        commandBox.runCommand("add p/important");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        //invalid command, name should be the first field entered
        commandBox.runCommand("add p/important homework");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd);
        assertTrue(addedCard != null);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
