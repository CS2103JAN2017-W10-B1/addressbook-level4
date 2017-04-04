//@@ author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.task.Event.MESSAGE_EVENT_CONSTRAINT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestEvent;
import seedu.address.testutil.TestRecurringTask;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

public class EditCommandTest extends TaskManagerGuiTest {

    private TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void editTask_changeAllTaskFields_editSuccess() throws Exception {
        String detailsToEdit = "n/lecture due/10/05/2017 dueT/16:00 #study d/Interesting module @I3 p/3 *u";
        int taskManagerIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("lecture").withDate("10/05/2017").withTag("study")
                .withTime("16:00").withDescription("Interesting module").withFinished(false).
                withVenue("I3").withPriority("3").withFavorite(false).build();
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editTask_someFieldsSpecified_editSuccess() throws Exception {
        String detailsToEdit = "due/10/05/2017 #newlist dueT/16:35 d/Random description p/trivial";
        int taskManagerIndex = 2;
        TestTask editedTask = new TestTask(expectedTasksList[taskManagerIndex - 1]);

        editedTask.setDate("10/05/2017");
        editedTask.setTag("newlist");
        editedTask.setTime("16:35");
        editedTask.setDescription("Random description");
        editedTask.setPriority("trivial");
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editTask_toBecomeEvent_editSuccess() throws Exception {
        String detailsToEdit = "start/03/03/2017 startT/10:00";
        int taskManagerIndex = 1;
        TestEvent editedTask = te.assignment;
        editedTask.setStartDate("03/03/2017");
        editedTask.setStartTime("10:00");
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editTask_toBecomeRecurringTask_editSuccess() {
        String detailsToEdit = "f/weekly";
        int taskManagerIndex = 2;
        TestRecurringTask editedTask = tr.gym;
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editEvent_toBecomeTask_editSuccess() throws Exception {
        expectedTasksList = TestUtil.addTasksToList(expectedTasksList, te.travel);
        TestEvent eventToAdd = te.travel;
        commandBox.runCommand(eventToAdd.getAddCommand());

        String detailsToEdit = "start/ startT/";
        int taskManagerIndex = 5;
        TestTask editedTask = new TestTask(expectedTasksList[taskManagerIndex - 1]);

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editEvent_withIllegalStartDate_editUnsuccess() {
        TestEvent eventToAdd = te.travel;
        commandBox.runCommand(eventToAdd.getAddCommand());

        String detailsToEdit = "start/01/02/2018";
        commandBox.runCommand("edit 5 " + detailsToEdit);
        assertResultMessage(MESSAGE_EVENT_CONSTRAINT);
    }
    @Test
    public void editEvent_withRecurrence_editFailure() {
        //TODO: after edit command is modified
    }

    @Test
    public void editRecurringTask_withStartDate_editFailure() {
        //TODO: after edit command is modified
    }

    @Test
    public void edit_invalidCommands_InvalidCommandFailure() {
        commandBox.runCommand("edit p/2");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_indexFailure() {
        commandBox.runCommand("edit 8 p/2");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_editUnsuccessful() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_constraintViolations() {
        commandBox.runCommand("edit 1 n/*&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS_1);

        commandBox.runCommand("edit 1 due/1111");
        assertResultMessage(TaskDate.MESSAGE_DATE_CONSTRAINTS_1);

        commandBox.runCommand("edit 1 dueT/1200");
        assertResultMessage(TaskTime.MESSAGE_TIME_CONSTRAINTS);

        commandBox.runCommand("edit 1 p/5");
        assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
    }

    /**
     * Checks whether the edited Task has the correct updated details.
     *
     * @param filteredTaskListIndex index of Task to edit in filtered list
     * @param addressBookIndex index of Task to edit in the address book.
     *      Must refer to the same Task as {@code filteredTaskListIndex}
     * @param detailsToEdit details to edit the Task with as input to the edit command
     * @param editedTask the expected Task after editing the Task's details
     */
    private void assertEditSuccess(int filteredTaskListIndex, int taskManagerIndex,
                                    String detailsToEdit, TestTask editedTask) {
        commandBox.runCommand("edit " + filteredTaskListIndex + " " + detailsToEdit);

        // confirm the new task is added to the current list
        assertTrue(taskListPanel.navigateToTask(editedTask));

        // confirm the list now contains all previous Tasks plus the Task with updated details
        expectedTasksList = TestUtil.replaceTaskFromList(expectedTasksList, editedTask, taskManagerIndex - 1);
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask.getName()));
    }

    @Test
    public void editValidEvent() throws Exception {
        commandBox.runCommand("add testevent due/today start/today startT/23:59");
        commandBox.runCommand("edit 1 n/testevent");

        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, "testevent"));
        commandBox.runCommand("delete 1");
    }

    @Test
    public void editDuplicateTasks() throws Exception {
        commandBox.runCommand("add testevent due/today start/today startT/23:59");
        commandBox.runCommand("add testevent2 due/today start/today startT/23:59");
        commandBox.runCommand("edit 2 n/testevent");

        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
        commandBox.runCommand("delete 1");
        commandBox.runCommand("delete 2");
    }

}
