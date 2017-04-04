///@@author A0147984L
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyRecurringTask;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * Edits the details of an existing recurring task in Dueue.
 */
public class EditNextCommand extends EditCommand {

    /**
     * Create editNext command using an index for specific task and description for the edited task
     */
    public EditNextCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        super(filteredTaskListIndex, editTaskDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        if (taskToEdit instanceof ReadOnlyRecurringTask) {
            throw new CommandException("The task to edit once should be a recurring task.");
        }

        try {
            Task newTask = createEditedTask(taskToEdit, editTaskDescriptor);
            model.addTask(newTask);
            model.finishTaskOnce(taskToEdit);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }

        model.updateFilteredListToShowAllUnfinishedTasks();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit.getName()));
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public CommandResult executeUndo(String message) throws CommandException {
        assert model != null;
        model.updateFilteredListToShowAllUnfinishedTasks();
        this.isSuccess = true;
        return new CommandResult(CommandFormatter.undoMessageFormatter(message, getUndoCommandWord()));
    }

    @Override
    public Command getUndoCommand() {
        if (isSuccess) {
            //TODO
            return null;
        } else {
            return new IncorrectCommand(null);
        }
    }

    @Override
    public String getUndoCommandWord() {
        return COMMAND_WORD + COMMAND_SUFFIX;
    }

}
