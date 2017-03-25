//@@author A0138474X
package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Event;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.Venue;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends AbleUndoCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to Dueue. "
            + "Parameters: TASKNAME [due/DUEDATE] [dueT/DUETIME] [start/STARTDATE] [startT/STARTTIME]"
            + " [#LISTNAME] [d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL]\n"
            + "Example: " + COMMAND_WORD
            + " CS2103 Lecture due/24/3 start/24/3 startT/16:00 dueT/18:00 #CS2103 d/Interesting module @I3 p/3 *f\n"
            + COMMAND_WORD
            + " CS2103T Tutorial due/8/3/2017 dueT/10:00 #CS2103 d/Interesting module @I3 p/2 *f\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Dueue";

    private final Task toAdd;
    private boolean isSuccess;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */

    public AddCommand(String name, String date, String start,String time, String startTime,
            String tag, String description, String venue, String priority, boolean isFavourite, boolean isEvent)
            throws IllegalValueException {

        if(isEvent){
            this.toAdd = new Event(
                    new Name(name),
                    new TaskDate(start),
                    new TaskTime (startTime),
                    new TaskDate(date),
                    new TaskTime(time),
                    new Description(description),
                    new Tag(tag),
                    new Venue(venue),
                    new Priority(priority),
                    isFavourite,
                    true
            );
        } else {
            this.toAdd = new Task(
                    new Name(name),
                    new TaskDate(date),
                    new TaskTime(time),
                    new Description(description),
                    new Tag(tag),
                    new Venue(venue),
                    new Priority(priority),
                    isFavourite,
                    false
                    );
            this.isSuccess = false;
        }
    }

    public AddCommand(ReadOnlyTask task) {
        this.toAdd = (Task) task;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            this.isSuccess = true;
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            this.isSuccess = false;
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    public Task getTask() {
        return toAdd;
    }

    @Override
    public boolean isUndoable() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public CommandResult executeUndo(String message) throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            this.isSuccess = true;
            return new CommandResult(String.format(message));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public Command getUndoCommand() {
        if (isSuccess) {
            return new DeleteCommand(getTask());
        } else {
            return new IncorrectCommand(null);
        }
    }
}
