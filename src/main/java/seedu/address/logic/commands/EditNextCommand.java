//@@author A0147984L
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.Event;
import seedu.address.model.task.ReadOnlyRecurringTask;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTask.RecurringProperty;
import seedu.address.model.task.RecurringEvent;
import seedu.address.model.task.RecurringTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits the details of an existing recurring task in Dueue.
 */
public class EditNextCommand extends EditCommand {

  //@@author A0143409J
    private ReadOnlyTask finishedOnceTask;

    /**
     * Create editNext command using an index for specific task and description for the edited task
     */
    public EditNextCommand(int filteredTaskListIndex, EditTaskDescriptor editTaskDescriptor) {
        super(filteredTaskListIndex, editTaskDescriptor);
        finishedOnceTask = null;
    }

    public EditNextCommand(ReadOnlyTask task, Task oldTask, ReadOnlyTask finishedOnceTask) {
        super(task, oldTask);
        this.finishedOnceTask = finishedOnceTask;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (filteredTaskListIndex >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(filteredTaskListIndex);
        if (!(taskToEdit instanceof ReadOnlyRecurringTask)) {
            throw new CommandException("The task to edit once should be a recurring task.");
        }
        ReadOnlyTask editTask = lastShownList.get(filteredTaskListIndex);

        try {
            if (editTask.isEvent()) {
                oldTask = new RecurringEvent(editTask);
            } else {
                oldTask = new RecurringTask(editTask);
            }
            Task newTask = createEditedTask(taskToEdit, editTaskDescriptor);
            newTask.setRecurringProperty(RecurringProperty.NON_RECURRING);
            if (newTask.isEvent()) {
                task = new Event(newTask);
            } else {
                task = new Task(newTask);
            }
            if (taskToEdit.isEvent()) {
                ((RecurringEvent) taskToEdit).finishOnce();
                finishedOnceTask = new RecurringEvent(taskToEdit);
            } else {
                ((RecurringTask) taskToEdit).finishOnce();
                finishedOnceTask = new RecurringTask(taskToEdit);
            }
            model.updateTask(filteredTaskListIndex, taskToEdit);
            model.addTask((Task) task);
            this.isSuccess = true;
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
        try {
            if (finishedOnceTask != null) {
                model.deleteTask(task);
                model.addTask(oldTask);
                model.deleteTask(finishedOnceTask);
                Task temp = (Task) task;
                this.task = oldTask;
                this.oldTask = temp;
                this.finishedOnceTask = null;
                this.isSuccess = true;
            } else {
                Task temp;
                if (oldTask.isEvent()) {
                    temp = new Event(oldTask);
                } else {
                    temp = new Task(oldTask);
                }
                model.addTask(oldTask);
                if (task.isEvent()) {
                    oldTask = new RecurringEvent(task);
                } else {
                    oldTask = new RecurringTask(task);
                }
                if (task.isEvent()) {
                    ((RecurringEvent) task).finishOnce();
                    finishedOnceTask = new RecurringEvent(task);
                } else {
                    ((RecurringTask) task).finishOnce();
                    finishedOnceTask = new RecurringTask(task);
                }
                model.deleteTask(task);
                model.addTask((Task) finishedOnceTask);
                this.task = temp;
                this.isSuccess = true;
            }
        } catch (TaskNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DuplicateTaskException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.updateFilteredListToShowAllUnfinishedTasks();
        isSuccess = true;
        return new CommandResult(CommandFormatter.undoMessageFormatter(message, getUndoCommandWord()));
    }

    @Override
    public Command getUndoCommand() {
        if (isSuccess) {
            //TODO
            return new EditNextCommand(task, oldTask, finishedOnceTask);
        } else {
            return null;
        }
    }

}
