//@@author A0138474X
package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringTask;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;


/**
 * Deletes a task identified using it's last displayed index from the Dueue.
 */
public class DeleteCommand extends AbleUndoCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_DELETE = "delete command";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 [all]";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    public final int targetIndex;

    private ReadOnlyTask task;
    private ReadOnlyTask replaceTask;

    private boolean isSuccess;
    private boolean isDeleteAllOcurrence;

    public DeleteCommand(int targetIndex, boolean isDeleteAllOcurrence) {
        this.targetIndex = targetIndex;
        this.isDeleteAllOcurrence = isDeleteAllOcurrence;
        this.task = null;
        this.isSuccess = false;
    }

    public DeleteCommand(ReadOnlyTask task, boolean isDeleteAllOcurrence) {
        this.targetIndex = 0;
        this.isDeleteAllOcurrence = isDeleteAllOcurrence;
        this.task = task;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            if (taskToDelete.isRecurring() && !isDeleteAllOcurrence) {
                try {
                    this.task = new RecurringTask(taskToDelete);
                    ((RecurringTask) taskToDelete).finishOnce();
                    this.replaceTask = new RecurringTask(taskToDelete);
                    isSuccess = true;
                    model.updateTask(targetIndex - 1, taskToDelete);
                } catch (DuplicateTaskException e) {
                    e.printStackTrace();
                }
            } else {
                model.deleteTask(taskToDelete);
                this.task = taskToDelete;
                this.isSuccess = true;
            }
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(
                CommandFormatter.undoFormatter(
                        String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete), COMMAND_DELETE));
    }


    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public CommandResult executeUndo(String message) throws CommandException {

        try {
            if (task.isRecurring() && !isDeleteAllOcurrence) {
                try {
                    RecurringTask task = new RecurringTask(this.task);
                    ((RecurringTask) task).finishOnce();
                    this.replaceTask = new RecurringTask(task);
                    isSuccess = true;
                    model.deleteTask(this.task);
                    model.addTask(task);
                } catch (DuplicateTaskException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalValueException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                model.deleteTask(this.task);
                this.isSuccess = true;
            }
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(CommandFormatter.undoMessageFormatter(message, COMMAND_DELETE));
    }

    @Override
    public Command getUndoCommand() {
        if (isSuccess) {
            if (task.isRecurring() && !isDeleteAllOcurrence) {
                try {
                    model.deleteTask(replaceTask);
                } catch (TaskNotFoundException e) {
                    assert false : "this task cannot be invalid";
                }
            }
            return new AddCommand(task, isDeleteAllOcurrence);
        } else {
            return new IncorrectCommand(null);
        }
    }

    @Override
    public String getUndoCommandWord() {
        return AddCommand.COMMAND_WORD + COMMAND_SUFFIX;
    }
}
