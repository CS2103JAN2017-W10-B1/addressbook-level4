package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
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
    public static final String MESSAGE_FINISH_TASK_MARKED = "Task had been finished: %1$s";
    public static final String MESSAGE_WRONG_TASK_INDEX = "This task already exists in the task manager.";

    public final int targetIndex;
    private Task task;
    private boolean isSuccess;

    public FinishCommand(int targetIndex) {
        this.targetIndex = targetIndex;
        this.isSuccess = false;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);

        if (taskToMark.isFinished()) {
            throw new CommandException(MESSAGE_FINISH_TASK_MARKED);
        } else {
            Name updatedName = taskToMark.getName();
            TaskDate updatedDate = taskToMark.getDate();
            TaskTime updatedTime = taskToMark.getTime();
            Description updatedDescription = taskToMark.getDescription();
            Tag updatedTag = taskToMark.getTag();
            Venue updatedVenue = taskToMark.getVenue();
            Priority updatedPriority = taskToMark.getPriority();

            taskToMark  = new Task(
                    updatedName, updatedDate, updatedTime, updatedDescription,
                    updatedTag, updatedVenue, updatedPriority, false, true);
        }

        try {
            model.updateTask(targetIndex - 1, taskToMark);
            task = (Task) taskToMark;
            isSuccess = true;
        } catch (DuplicateTaskException e) {
            this.isSuccess = false;
            throw new CommandException(MESSAGE_WRONG_TASK_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_FINISH_TASK_SUCCESS, taskToMark));
    }


    @Override
    public boolean isUndoable() {
        return true;
    }


    @Override
    public CommandResult executeUndo(String message) throws CommandException {
        return null;
    }


    @Override
    public Command getUndoCommand() {
        if (isSuccess) {
            Task newTask = new Task(task.getName(), task.getDate(), task.getTime(), task.getDescription(),
                    task.getTag(), task.getVenue(), task.getPriority(), task.isFavorite(), false);
            return new EditCommand(task, newTask);
        } else {
            return new IncorrectCommand(null);
        }
    }

}

