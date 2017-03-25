//@@ author A0147996E
package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of Tasks in the Task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    private TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void editAllFieldsSpecifiedSuccess() throws Exception {
        String detailsToEdit = "n/lecture due/10/04/2018 t/16:00 #study d/Interesting module @I3 p/3 *u";
        int taskManagerIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("lecture").withDate("10/04/2018").withTag("study")
                .withTime("16:00").withDescription("Interesting module").
                withVenue("I3").withPriority("3").withFavorite(false).build();
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editSomeFieldsSpecifiedSuccess() throws Exception {
        String detailsToEdit = "due/10/04/2018 #newlist t/16:35 d/Random description p/trivial";
        int taskManagerIndex = 2;
        TestTask editedTask = new TestTask(expectedTasksList[taskManagerIndex - 1]);

        editedTask.setDate("10/04/2018");
        editedTask.setTag("newlist");
        editedTask.setTime("16:35");
        editedTask.setDescription("Random description");
        editedTask.setPriority("trivial");
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editNotAllFieldsSpecifiedSuccess() throws Exception {
        String detailsToEdit = "p/2";
        int taskManagerIndex = 2;

        TestTask taskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(taskToEdit).withPriority("2").build();
        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editFindThenEditSuccess() throws Exception {
        commandBox.runCommand("find study");

        String detailsToEdit = "n/gym";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 1;

        TestTask TaskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(TaskToEdit).withName("gym").build();

        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void editMissingTaskIndexFailure() {
        commandBox.runCommand("edit p/2");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void editInvalidTaskIndexFailure() {
        commandBox.runCommand("edit 8 p/2");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void editNoFieldsSpecifiedFailure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void editInvalidValuesFailure() {
        commandBox.runCommand("edit 1 n/*&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS_1);

        commandBox.runCommand("edit 1 due/1111");
        assertResultMessage(TaskDate.MESSAGE_DATE_CONSTRAINTS_1);

        commandBox.runCommand("edit 1 t/1200");
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

        // confirm the new card contains the right data
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous Tasks plus the Task with updated details
        expectedTasksList = TestUtil.replaceTaskFromList(expectedTasksList, editedTask, taskManagerIndex - 1);
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
