//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add_task() {
        //Start testing with an empty list
        TestTask[] currentList = {};
        commandBox.runCommand("clear");

        //add a floating task with name only
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
    }

    @Test
    public void addEventToList() {
        //Start testing with an empty list
        TestTask[] currentList = {};

        //add an event to current task list
        TestEvent eventToAdd = te.date;
        assertAddSuccess(eventToAdd, currentList);
        currentList = (TestUtil.addEventsToList(currentList, eventToAdd));

        //add another task with other fields other than name
        TestTask taskToAdd = td.familyDinner;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

      //add an event to test duplication to current list
        eventToAdd = te.date2;
        assertAddSuccess(eventToAdd, currentList);
        currentList = (TestUtil.addEventsToList(currentList, eventToAdd));

        //add an event to test duplication to current list
        eventToAdd = te.date3;
        assertAddSuccess(eventToAdd, currentList);
        currentList = (TestUtil.addEventsToList(currentList, eventToAdd));
    }

    @Test
    public void invalid_command () {
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

        //AssertTrue if can navigate to the task card in current list view that matches the taskToAdd
        assertTrue(taskListPanel.navigateToTask(taskToAdd));

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
}
