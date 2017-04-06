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
    // indicating whether this command in undoing another command
    private boolean isUndo = false;

    public DeleteCommand(int targetIndex, boolean isDeleteAllOcurrence) {
        this.targetIndex = targetIndex;
        this.isDeleteAllOcurrence = isDeleteAllOcurrence;
        this.task = null;
        this.isSuccess = false;
    }

    public DeleteCommand(ReadOnlyTask task, boolean isDeleteAllOcurrence) {
        this.targetIndex = 0;
        this.isDeleteAllOcurrence = isDeleteAllOcurrence;
        this.taskToDelete = task;
        this.isUndo = true;
    }


    @Override
    public CommandResult execute() throws CommandException {
        if (!isUndo) {
            return execute(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        } else {
            return execute(UndoCommand.MESSAGE_SUCCESS);
        }
    }

    public CommandResult execute(String message) throws CommandException {

        if (!isUndo) {
            processData();
        }
        return delete(taskToDelete, message);
    }


    private CommandResult delete(ReadOnlyTask deleteTask, String message) {
        try {
            if (deleteTask.isRecurring() && !isDeleteAllOcurrence) {
                try {
                    Task task = (Task) createRecurringTask(deleteTask);
                    finishOnce(task);
                    this.replaceTask = createRecurringTask(task);
                    isSuccess = true;
                    model.deleteTask(deleteTask);
                    model.addTask(task);
                    this.task = deleteTask;
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
        return new CommandResult(CommandFormatter.undoFormatter(message, COMMAND_DELETE));
    }

    private void finishOnce(ReadOnlyTask deleteTask) {
        if (!deleteTask.isEvent()) {
            ((RecurringTask) deleteTask).finishOnce();
        } else {
            ((RecurringEvent) deleteTask).finishOnce();
        }
    }

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
    public CommandResult executeUndo(String message) throws CommandException {
        return execute();
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
            return null;
        }
    }

    @Override
    public String getUndoCommandWord() {
        return AddCommand.COMMAND_WORD + COMMAND_SUFFIX;
    }
}
