//@@Author ShermineJong A0138474X
package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;


/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends AbleUndoCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    public final int targetIndex;

    private ReadOnlyTask task;

    private boolean isSuccess;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.task = null;
        this.isSuccess = false;
    }

    public DeleteCommand(ReadOnlyTask task) {
        this.targetIndex = 0;
        this.task = task;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask personToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(personToDelete);
            this.task = personToDelete;
            this.isSuccess = true;
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, personToDelete));
    }


    @Override
    public boolean isUndoable() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public CommandResult executeUndo() throws CommandException {
        try {
            model.deleteTask(task);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_UNDO_TASK_SUCCESS));
    }

    @Override
    public Command getUndoCommand() {
        if (isSuccess) {
            return new AddCommand(task);
        } else {
            return new IncorrectCommand(null);
        }
    }

}
