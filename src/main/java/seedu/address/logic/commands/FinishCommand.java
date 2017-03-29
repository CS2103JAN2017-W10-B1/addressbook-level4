//@@author A0138474X
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Event;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTask.FinishProperty;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.Venue;


/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class FinishCommand extends AbleUndoCommand {

    public static final String COMMAND_WORD = "finish";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as finished.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FINISH_TASK_SUCCESS = "Mark finished task: %1$s";
    public static final String MESSAGE_FINISH_TASK_MARKED = "Task had already been finished";
    public static final String MESSAGE_WRONG_TASK_INDEX = "This task already exists in the task manager.";

    public final int targetIndex;
    private boolean isSuccess;
    private boolean isDeleted;
    private Task task;

    public FinishCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.isSuccess = false;
        this.isDeleted = false;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);
        
        ReadOnlyTask editedTask = null;

        if (taskToMark.isFinished()) {
            throw new CommandException(MESSAGE_FINISH_TASK_MARKED);
        } else {
            Name updatedName = taskToMark.getName();
            TaskDate updatedDueDate = taskToMark.getDate();
            TaskTime updatedDueTime = taskToMark.getTime();
            Description updatedDescription = taskToMark.getDescription();
            Tag updatedTag = taskToMark.getTag();
            Venue updatedVenue = taskToMark.getVenue();
            Priority updatedPriority = taskToMark.getPriority();
            boolean updatedFavorite = taskToMark.isFavorite();
            FinishProperty updatedFinish = FinishProperty.FINISHED;

            if (taskToMark.isEvent()) {
                TaskDate updatedStartDate = ((Event) taskToMark).getStartDate();
                TaskTime updatedStartTime = ((Event) taskToMark).getStartTime();
                try {
                    editedTask = new Event(updatedName, updatedStartDate, updatedStartTime,
                            updatedDueDate, updatedDueTime, updatedDescription, updatedTag,
                            updatedVenue, updatedPriority, updatedFavorite, updatedFinish);
                } catch (IllegalValueException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                editedTask  = new Task(
                        updatedName, updatedDueDate, updatedDueTime, updatedDescription,
                        updatedTag, updatedVenue, updatedPriority, updatedFavorite, updatedFinish);
            }
        }

        try {
            model.updateTask(targetIndex - 1, editedTask);
            task = (Task)editedTask;
            isSuccess = true;
        } catch (DuplicateTaskException e) {
            this.isSuccess = false;
            try {
                model.deleteTask(taskToMark);
                task = (Task)editedTask;
                isSuccess = true;
                isDeleted = true;
            } catch (TaskNotFoundException e1) {
                assert false : "The target person cannot be missing";
            }
        }

        return new CommandResult(String.format(MESSAGE_FINISH_TASK_SUCCESS, taskToMark));
    }

    @Override
    public boolean isUndoable() {
        return true;
    }


    @Override
    public CommandResult executeUndo(String message) throws CommandException {
        if (!isDeleted) {
            try {
                model.deleteTask(task);
            } catch (TaskNotFoundException e) {
                assert false : "The target task cannot be missing";
            }
        }
        Task oldTask = null;
        FinishProperty finish;
        if (task.isFinished()) {
            finish = FinishProperty.UNFINISHED;
        } else {
            finish = FinishProperty.FINISHED;
        }
        if (task.isEvent()) {
            try {
                oldTask = new Event(task.getName(), ((Event) task).getStartDate(), ((Event) task).getStartTime(), 
                        task.getDate(), task.getTime(), task.getDescription(), task.getTag(), task.getVenue(), 
                        task.getPriority(), task.isFavorite(), finish);
            } catch (IllegalValueException e) {
                assert false : "The event must be valid";
            }
        } else {
            oldTask = new Task(task.getName(), task.getDate(), task.getTime(), task.getDescription(), task.getTag(), 
                    task.getVenue(), task.getPriority(), task.isFavorite(), finish);
        }
        try {
            model.addTask(oldTask);
            isDeleted = false;
            task = oldTask;
        } catch (DuplicateTaskException e) {
            assert false : "There must not be duplicated task";
        }
        model.updateFilteredListToShowAllUnfinishedTasks();
        this.isSuccess = true;
        return new CommandResult(message);
    }


    @Override
    public Command getUndoCommand() throws IllegalValueException {
        if (isSuccess) {
            return this;
        } else {
            return new IncorrectCommand(null);
        }
    }
}

