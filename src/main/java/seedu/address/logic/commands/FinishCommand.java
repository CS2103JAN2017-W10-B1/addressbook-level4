package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Date;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.Time;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.Venue;


/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class FinishCommand extends Command {

    public static final String COMMAND_WORD = "finish";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as finished.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FINISH_TASK_SUCCESS = "Mark finished task: %1$s";
    public static final String MESSAGE_FINISH_TASK_MARKED = "Task had been finished: %1$s";
    public static final String MESSAGE_WRONG_TASK_INDEX = "This task already exists in the task manager.";

    public final int targetIndex;

    public FinishCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);

        if(taskToMark.isFinished()){
            throw new CommandException(MESSAGE_FINISH_TASK_MARKED) ;
        }
        else{
        	Name updatedName = taskToMark.getName();
        	Date updatedDate = taskToMark.getDate();
        	Time updatedTime = taskToMark.getTime();
        	Description updatedDescription = taskToMark.getDescription();
        	Tag updatedTag = taskToMark.getTag();
        	Venue updatedVenue = taskToMark.getVenue();
        	Priority updatedPriority = taskToMark.getPriority();

        	taskToMark  = new Task(
        	        updatedName, updatedDate, updatedTime, updatedDescription, 
        	        updatedTag, updatedVenue, updatedPriority, false, true);
        }
     
        try {
			model.updateTask(targetIndex-1, taskToMark);
		} catch (DuplicateTaskException e) {
			throw new CommandException(MESSAGE_WRONG_TASK_INDEX);
		}

        return new CommandResult(String.format(MESSAGE_FINISH_TASK_SUCCESS, taskToMark));
    }

}

