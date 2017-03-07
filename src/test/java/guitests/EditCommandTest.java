package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.task.Name;
import seedu.address.model.task.Date;
import seedu.address.model.task.Time;
import seedu.address.model.task.Priority;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

// TODO: reduce GUI tests by transferring some tests to be covered by lower level tests.
public class EditCommandTest extends TaskManagerGuiTest {

    // The list of Tasks in the Task list panel is expected to match this list.
    // This list is updated with every successful call to assertEditSuccess().
    TestTask[] expectedTasksList = td.getTypicalTasks();

    @Test
    public void edit_allFieldsSpecified_success() throws Exception {
        String detailsToEdit = "n/Lecture due/10/3/2017 t/16:00 d/Interesting module @I3 p/3 *f";
        int taskManagerIndex = 1;

        TestTask editedTask = new TaskBuilder().withName("Lecture").withDate("10/3/2017")
                .withTime("16:00").withDescription("Interesting module").withVenue("I3").withPriority("3").withFavorite(true).build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_notAllFieldsSpecified_success() throws Exception {
        String detailsToEdit = "p/2";
        int taskManagerIndex = 2;

        TestTask TaskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(TaskToEdit).withPriority("2").build();

        assertEditSuccess(taskManagerIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_findThenEdit_success() throws Exception {
        commandBox.runCommand("find gym");

        String detailsToEdit = "gym";
        int filteredTaskListIndex = 1;
        int taskManagerIndex = 5;

        TestTask TaskToEdit = expectedTasksList[taskManagerIndex - 1];
        TestTask editedTask = new TaskBuilder(TaskToEdit).withName("gym").build();

        assertEditSuccess(filteredTaskListIndex, taskManagerIndex, detailsToEdit, editedTask);
    }

    @Test
    public void edit_missingTaskIndex_failure() {
        commandBox.runCommand("edit gym");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void edit_invalidTaskIndex_failure() {
        commandBox.runCommand("edit 8 gym");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void edit_noFieldsSpecified_failure() {
        commandBox.runCommand("edit 1");
        assertResultMessage(EditCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void edit_invalidValues_failure() {
        commandBox.runCommand("edit 1 *&");
        assertResultMessage(Name.MESSAGE_NAME_CONSTRAINTS);

        commandBox.runCommand("edit 1 due/1111");
        assertResultMessage(Date.MESSAGE_DATE_CONSTRAINTS);

        commandBox.runCommand("edit 1 t/#421");
        assertResultMessage(Time.MESSAGE_TIME_CONSTRAINTS);

        commandBox.runCommand("edit 1 p/5");
        assertResultMessage(Priority.MESSAGE_PRIORITY_CONSTRAINTS);
    }

    @Test
    public void edit_duplicateTask_failure() {
        commandBox.runCommand("edit n/Lecture due/10/3/2017 t/16:00"
                                + "d/Interesting module @I3 p/3 *f");
        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
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
        TaskCardHandle editedCard = taskListPanel.navigateToTask(editedTask.getName().fullName);
        assertMatching(editedTask, editedCard);

        // confirm the list now contains all previous Tasks plus the Task with updated details
        expectedTasksList[taskManagerIndex - 1] = editedTask;
        assertTrue(taskListPanel.isListMatching(expectedTasksList));
        assertResultMessage(String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }
}
