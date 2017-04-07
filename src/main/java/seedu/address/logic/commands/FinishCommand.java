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
import seedu.address.model.task.RecurringEvent;
import seedu.address.model.task.RecurringTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.Venue;

/**
 * Finishes an unfinished task/event/recurring task in Dueue.
 */
public class FinishCommand extends AbleUndoCommand {

    public static final String COMMAND_WORD = "finish";

    public static final String COMMAND_FINISH = "finish command";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as finished.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FINISH_TASK_SUCCESS = "Mark finished task: %1$s. Good job!";
    public static final String MESSAGE_FINISH_TASK_MARKED = "Oops! This task had already been finished";
    public static final String MESSAGE_WRONG_TASK_INDEX = "Oops! This task already exists in Dueue.";

    public final int targetIndex;
    // indicate whether the finish is successful
    private boolean isSuccess;
    // indicate whether the task is deleted due to the duplicated task
    private boolean isDeleted;
    // the task to be added if undoing
    private Task task;
    // the task to be deleted if undoing or the edited finished task if not undoing
    private Task replaceTask;
    // indicate whether the task is undoing a finish command
    private boolean isUndo;

    public FinishCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.isSuccess = false;
        this.isDeleted = false;
    }


    @Override
    public CommandResult execute() throws CommandException {
        processTask();

        return execute(String.format(MESSAGE_FINISH_TASK_SUCCESS, replaceTask.getName()));
    }

    /*
     * update the mark task
     * if the task is not undoing a previous task the task update the model
     * if there is a duplicate task delete the task to replace
     * if undoing update by deleting and adding
     * if the there is a duplicate task then do not delete just add
     */
    public CommandResult execute(String message) throws CommandException {

        if (!isUndo) {
            try {
                model.updateTask(targetIndex - 1, replaceTask);
                isSuccess = true;
            } catch (DuplicateTaskException e) {
                try {
                    model.deleteTask(replaceTask);
                    isSuccess = true;
                    isDeleted = true;
                } catch (TaskNotFoundException e1) {
                    assert false : "The target person cannot be missing";
                }
            }
        } else {
            if (!isDeleted) {
                try {
                    model.deleteTask(replaceTask);
                } catch (TaskNotFoundException e) {
                    assert false : "The target task cannot be missing";
                }
            }
            try {
                model.addTask(task);
                isDeleted = false;
                if (task.isEvent() && task.isRecurring()) {
                    try {
                        Task temp = new RecurringEvent(task);
                        this.task = new RecurringEvent(replaceTask);
                        this.replaceTask = new RecurringEvent(temp);
                    } catch (IllegalValueException e) {
                        assert false : "The event must be valid";
                    }
                } else if (task.isEvent()) {
                    try {
                        Task temp = new Event(task);
                        this.task = new Event(replaceTask);
                        this.replaceTask = new Event(temp);
                    } catch (IllegalValueException e) {
                        assert false : "The event must be valid";
                    }
                } else if (task.isRecurring()) {
                    Task temp = new RecurringTask(task);
                    this.task = new RecurringTask(replaceTask);
                    this.replaceTask = new RecurringTask(temp);
                } else {
                    Task temp = new Task(task);
                    this.task = new Task(replaceTask);
                    this.replaceTask = new Task(temp);
                }
            } catch (DuplicateTaskException e) {
                assert false : "There must not be duplicated task";
            }
            model.updateFilteredListToShowAllUnfinishedTasks();
            this.isSuccess = true;
        }

        return new CommandResult(message);
    }

    // create the mark task
    private void processTask() throws CommandException {
        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);

        ReadOnlyTask editedTask = null;

        if (taskToMark.isFinished()) {
            throw new CommandException(MESSAGE_FINISH_TASK_MARKED);
        } else if (taskToMark.isRecurring()) {
            try {
                if (taskToMark.isEvent()) {
                    this.task = new RecurringEvent(taskToMark);
                    editedTask = new RecurringEvent(taskToMark);
                    ((RecurringEvent) editedTask).finishOnce();
                    this.replaceTask = new RecurringEvent(editedTask);
                } else {
                    this.task = new RecurringTask(taskToMark);
                    editedTask = new RecurringTask(taskToMark);
                    ((RecurringTask) editedTask).finishOnce();
                    this.replaceTask = new RecurringTask(editedTask);
                }
            } catch (IllegalValueException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            if (taskToMark.isEvent()) {
                try {
                    this.task = new Event(taskToMark);
                    editedTask = new Event(new Name(taskToMark.getName().fullName),
                                 new TaskDate(((Event) taskToMark).getStartDate().getValue()),
                                 new TaskTime(((Event) taskToMark).getStartTime().getValue()),
                                 new TaskDate(taskToMark.getDate().getValue()),
                                 new TaskTime(taskToMark.getTime().getValue()),
                                 new Description(taskToMark.getDescription().getValue()),
                                 new Tag(taskToMark.getTag().tagName),
                                 new Venue(taskToMark.getVenue().getValue()),
                                 new Priority(taskToMark.getPriority().getValue()),
                                 taskToMark.isFavorite(),
                                 FinishProperty.FINISHED);
                    this.replaceTask = new Event(editedTask);
                } catch (IllegalValueException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                this.task = new Task(taskToMark);
                try {
                    editedTask  = new Task(
                            new Name(taskToMark.getName().fullName),
                            new TaskDate(taskToMark.getDate().getValue()),
                            new TaskTime(taskToMark.getTime().getValue()),
                            new Description(taskToMark.getDescription().getValue()),
                            new Tag(taskToMark.getTag().tagName),
                            new Venue(taskToMark.getVenue().getValue()),
                            new Priority(taskToMark.getPriority().getValue()),
                            taskToMark.isFavorite(),
                            FinishProperty.FINISHED);
                } catch (IllegalValueException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                this.replaceTask = new Task(editedTask);
            }
        }

    }


    @Override
    public boolean isUndoable() {
        return true;
    }


    @Override
    public CommandResult executeUndo(String message) throws CommandException {
        return execute(CommandFormatter.undoMessageFormatter(message, getRedoCommandWord()));
    }

    // get the command to undo this command
    @Override
    public Command getUndoCommand() throws IllegalValueException {
        if (isSuccess) {
            isUndo = true;
            return this;
        } else {
            return null;
        }
    }

    @Override
    public String getRedoCommandWord() {
        return COMMAND_WORD + COMMAND_SUFFIX;
    }
}

