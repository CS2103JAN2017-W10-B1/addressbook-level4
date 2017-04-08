//@@author A0138474X
package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringEvent;
import seedu.address.model.task.RecurringTask;
import seedu.address.model.task.Task;
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
    private ReadOnlyTask taskToDelete;
    private ReadOnlyTask replaceTask;
    // indicating whether the command is executed correctly
    private boolean isSuccess = false;
    // indicate to delete all occurrence for occurring task and event
    private boolean isDeleteAllOcurrence;

    public DeleteCommand(int targetIndex, boolean isDeleteAllOcurrence) {
        this.targetIndex = targetIndex;
        this.isDeleteAllOcurrence = isDeleteAllOcurrence;
        this.task = null;
        this.isSuccess = false;
    }

    // constructor for undoing an add command
    public DeleteCommand(ReadOnlyTask task, boolean isDeleteAllOcurrence) {
        this.targetIndex = 0;
        this.isDeleteAllOcurrence = isDeleteAllOcurrence;
        this.taskToDelete = task;
    }


    @Override
    public CommandResult execute() throws CommandException {
        processData();
        return execute(CommandFormatter.undoFormatter(String.format(MESSAGE_DELETE_TASK_SUCCESS, this.taskToDelete)
                , COMMAND_DELETE));
    }

    /*
     * Delete a task
     * If user request to delete a recurring task and did not specify all the recurring task will be finish once
     */
    public CommandResult execute(String message) throws CommandException {
        try {
            if (taskToDelete.isRecurring() && !isDeleteAllOcurrence) {
                try {
                    Task task = (Task) createRecurringTask(taskToDelete);
                    finishOnce(task);
                    this.replaceTask = createRecurringTask(task);
                    isSuccess = true;
                    model.deleteTask(taskToDelete);
                    model.addTask(task);
                    this.task = taskToDelete;
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
        return new CommandResult(message);
    }

    private void finishOnce(ReadOnlyTask deleteTask) {
        if (!deleteTask.isEvent()) {
            ((RecurringTask) deleteTask).finishOnce();
        } else {
            ((RecurringEvent) deleteTask).finishOnce();
        }
    }

    // create a recurring task to be store for undoing the delete
    private ReadOnlyTask createRecurringTask(ReadOnlyTask recurringTask) {
        Task task = null;
        if (recurringTask.isEvent()) {
            try {
                task = new RecurringEvent(recurringTask);
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            task =  new RecurringTask(recurringTask);
        }
        return task;
    }

    // get the task to be deleted from the modelManager
    private void processData() throws CommandException {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        taskToDelete = lastShownList.get(targetIndex - 1);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public CommandResult undo(String message) throws CommandException {
        return execute(CommandFormatter.undoMessageFormatter(message, COMMAND_WORD + " command"));
    }

    // Get the command that is equivalent to undoing a delete command (AddCommand)
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
            return null;
        }
    }

    @Override
    public String getUndoCommandWord() {
        return AddCommand.COMMAND_WORD + COMMAND_SUFFIX;
    }

    @Override
    public String getRedoCommandWord() {
        return COMMAND_WORD + COMMAND_SUFFIX;
    }
}
