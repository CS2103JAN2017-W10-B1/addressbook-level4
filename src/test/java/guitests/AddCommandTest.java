//@@author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddCommand.COMMAND_ADD;
import static seedu.address.logic.commands.AddCommand.MESSAGE_SUCCESS;
import static seedu.address.model.task.Event.MESSAGE_EVENT_CONSTRAINT;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandFormatter;
import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestRecurringTask;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class AddCommandTest extends TaskManagerGuiTest {
    private TestTask[] currentList = {};

    @Before
    public void setUp() {
        String initCommand = "clear";
        commandBox.runCommand(initCommand);
    }

    @Test
    public void add_addFloatingTask_addSuccess() {
        TestTask taskToAdd = td.shopping2;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
    }

    @Test
    public void add_addInvalidDuplicate_duplicateFailure() {
         //add a task with all fields specified first
        TestTask taskToAdd = td.date;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //duplicate task tests
        commandBox.runCommand(td.date.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void add_addValidDuplicates_addSuccess() {
        TestTask taskToAdd = td.date2;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        taskToAdd = td.date3;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        taskToAdd = td.date4;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
    }

    @Test
    public void add_addEventWithIllegalStartDate_addUnsuccess() {
        TestEvent eventToAdd = te.shopping;
        commandBox.runCommand(eventToAdd.getAddCommand());
        assertResultMessage(MESSAGE_EVENT_CONSTRAINT);
    }

    @Test
    public void add_addEventsWithSomeDuplicateFields_success() {
        TestEvent eventToAdd = te.date;
        assertAddSuccess(eventToAdd, currentList);
        currentList = (TestUtil.addTasksToList(currentList, eventToAdd));

        eventToAdd = te.date2;
        assertAddSuccess(eventToAdd, currentList);
        currentList = (TestUtil.addTasksToList(currentList, eventToAdd));

        eventToAdd = te.date3;
        assertAddSuccess(eventToAdd, currentList);
        currentList = (TestUtil.addTasksToList(currentList, eventToAdd));
    }

    @Test
    public void add_addRecurringTask_success() {
        TestRecurringTask recurringTaskToAdd = tr.homework;
        assertAddSuccess(recurringTaskToAdd, currentList);
        currentList = (TestUtil.addTasksToList(currentList, recurringTaskToAdd));

        recurringTaskToAdd = tr.homework2;
        assertAddSuccess(recurringTaskToAdd, currentList);
        currentList = (TestUtil.addTasksToList(currentList, recurringTaskToAdd));

        recurringTaskToAdd = tr.cs2103;
        assertAddSuccess(recurringTaskToAdd, currentList);
        currentList = (TestUtil.addTasksToList(currentList, recurringTaskToAdd));
    }

    @Test
    public void addCommand_invalidCommandFormat_failure () {
        commandBox.runCommand("addhomework");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("add p/important");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

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
        assertResultMessage(CommandFormatter.undoFormatter(
                String.format(MESSAGE_SUCCESS, taskToAdd.getName()), COMMAND_ADD));
    }
}
