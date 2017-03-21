//@@Author ShermineJong A0138474X
package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.Venue;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to Dueue. "
            + "Parameters: TASKNAME [due/DUEDATE] [t/TIME] [#LISTNAME] [d/DESCRIPTION] [@VENUE] [p/PRIORITYLEVEL]\n"
            + "Example: " + COMMAND_WORD
            + " CS2103 Lecture due/10/3 t/16:00 #CS2103 d/Interesting module @I3 p/3 \n"
            + COMMAND_WORD
            + " CS2103T Tutorial due/8/3/2017 t/10:00 #CS2103 d/Interesting module @I3 p/2 \n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Dueue";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */

    public AddCommand(String name, String date, String time,
            String tag, String description, String venue, String priority, boolean isFavourite)
            throws IllegalValueException {

        this.toAdd = new Task(
                new Name(name),
                new TaskDate(date),
                new TaskTime(time),
                new Description(description),
                new Tag(tag),
                new Venue(venue),
                new Priority(priority),
                isFavourite
        );
    }

    public AddCommand(ReadOnlyTask task) {
        this.toAdd = (Task) task;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
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
    public CommandResult executeUndo() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_UNDO_TASK_SUCCESS));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public Command getUndoCommand() {
        return new DeleteCommand(getTask());
    }
}
