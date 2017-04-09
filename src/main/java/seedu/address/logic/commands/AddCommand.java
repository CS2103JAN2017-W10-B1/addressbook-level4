//@@author A0138474X
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.Event;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyRecurringTask.RecurringMode;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.RecurringEvent;
import seedu.address.model.task.RecurringTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskTime;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.Venue;

/**
 * Adds a task/event/recurring task to Dueue.
 */
public class AddCommand extends AbleUndoCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ADD = COMMAND_WORD + COMMAND_SUFFIX;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to Dueue. \n"
            + "Parameters: NAME [due/DUE_DATE] [dueT/DUE_TIME] [start/START_DATE] [startT/START_TIME]\n"
            + "[#LISTNAME] [d/DESCRIPTION] [@VENUE] [p/PRIORITY_LEVEL] [*f] [f/REPEAT_PERIOD] \n"
            + "Examples: " + COMMAND_WORD
            + " \nTrip to Taiwan due/24/6/2017 start/tmr startT/16:00"
            + " dueT/18:00 p/3 *f\n"
            + COMMAND_WORD
            + " CS2103T Tutorial #CS2103 d/Finish asap p/important\n"
            + COMMAND_WORD
            + " CS2103 Demo f/weekly #CS2103 d/Exhausting module @SoC p/3\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Dueue";

    private final Task toAdd;
    // indicate whether the command had been executed successfully
    private boolean isSuccess = false;
    // indicate for undoing deleting of occurring Task or Events
    private boolean isDeleteAllOcurrence = true;
    // indicate whether this command is performing undo for another command
    private boolean isUndo = false;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */

    public AddCommand(String name, String date, String startDate, String time, String startTime, String tag,
            String description, String venue, String priority, String frequency, boolean isFavourite,
            boolean isEvent, boolean isRecurring) throws IllegalValueException {
        RecurringMode mode = getRecurringMode(frequency);
        if (isEvent && isRecurring) {
            this.toAdd = buildRecurringEvent(name, date, startDate, time, startTime,
                    tag, description, venue, priority, isFavourite, mode);
        } else if (isEvent) {
            this.toAdd = buildEvent(name, date, startDate, time, startTime,
                    tag, description, venue, priority, isFavourite);
        } else if (isRecurring) {
            this.toAdd = buildRecurringTask(name, date, time,
                    tag, description, venue, priority, isFavourite, mode);
        } else {
            this.toAdd = buildTask(name, date, time, tag, description,
                    venue, priority, isFavourite);
        }
    }

    // constructor for undoing a delete command
    public AddCommand(ReadOnlyTask task, boolean isDeleteAllOcurrence) {
        this.toAdd = (Task) task;
        this.isDeleteAllOcurrence = isDeleteAllOcurrence;
        this.isUndo = true;
    }

    //create an event to add to Dueue
    private Event buildEvent(String name, String date, String startDate, String time, String startTime, String tag,
            String description, String venue, String priority, boolean isFavourite) throws IllegalValueException {
        if (!startDate.isEmpty()) {
            return new Event(
                    new Name(name),
                    new TaskDate(startDate),
                    new TaskTime (startTime),
                    new TaskDate(date),
                    new TaskTime(time),
                    new Description(description),
                    new Tag(tag),
                    new Venue(venue),
                    new Priority(priority),
                    isFavourite
                    );
        } else {
            return new Event(
                    new Name(name),
                    new TaskDate(date),
                    new TaskTime (startTime),
                    new TaskTime(time),
                    new Description(description),
                    new Tag(tag),
                    new Venue(venue),
                    new Priority(priority),
                    isFavourite
                    );
        }
    }

    // create a recurring event to add to Dueue
    private Event buildRecurringEvent(String name, String date, String startDate, String time, String startTime,
            String tag, String description, String venue, String priority, boolean isFavourite, RecurringMode mode)
            throws IllegalValueException {
        if (mode == null) {
            return buildEvent(name, date, startDate, time, startTime, tag, description, venue, priority, isFavourite);
        } else {
            if (!startDate.isEmpty()) {
                return new RecurringEvent(
                        new Name(name),
                        new TaskDate(startDate),
                        new TaskTime (startTime),
                        new TaskDate(date),
                        new TaskTime(time),
                        new Description(description),
                        new Tag(tag),
                        new Venue(venue),
                        new Priority(priority),
                        isFavourite,
                        mode
                        );
            } else {
                return new RecurringEvent(
                        new Name(name),
                        new TaskDate(date),
                        new TaskTime (startTime),
                        new TaskTime(time),
                        new Description(description),
                        new Tag(tag),
                        new Venue(venue),
                        new Priority(priority),
                        isFavourite,
                        mode
                        );
            }
        }
    }

    // create a task to add into Dueue
    private Task buildTask(String name, String date, String time, String tag, String description, String venue,
            String priority, boolean isFavourite) throws IllegalValueException {
        return new Task(
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

    // create a recurring task to add into Dueue
    private Task buildRecurringTask(String name, String date, String time, String tag, String description, String venue,
            String priority, boolean isFavourite, RecurringMode mode) throws IllegalValueException {
        if (mode == null) {
            return buildTask(name, date, time, tag,
                    description, venue, priority, isFavourite);
        } else {
            return new RecurringTask(
                    new Name(name),
                    new TaskDate(date),
                    new TaskTime(time),
                    new Description(description),
                    new Tag(tag),
                    new Venue(venue),
                    new Priority(priority),
                    isFavourite,
                    mode
                    );
        }
    }

    // convert the string to RecurringMode
    private RecurringMode getRecurringMode(String ocurring) {
        if (ocurring.matches(RecurringTask.PERIOD_DAY_REGEX)) {
            return RecurringMode.DAY;
        } else if (ocurring.matches(RecurringTask.PERIOD_WEEK_REGEX)) {
            return RecurringMode.WEEK;
        } else if (ocurring.matches(RecurringTask.PERIOD_MONTH_REGEX)) {
            return RecurringMode.MONTH;
        }
        return null;
    }

    @Override
    public CommandResult execute() throws CommandException {
        return execute(CommandFormatter.undoFormatter(
                String.format(MESSAGE_SUCCESS, toAdd.getName()), COMMAND_ADD));
    }

    public CommandResult execute(String message) throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            this.isSuccess = true;
            int taskIndex = model.getFilteredTaskList().indexOf(toAdd);
            EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(taskIndex));

            return new CommandResult(message);
        } catch (UniqueTaskList.DuplicateTaskException e) {
            this.isSuccess = false;
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public CommandResult undo(String message) throws CommandException {
        return execute(CommandFormatter.undoMessageFormatter(message, getRedoCommandWord()));
    }

    // return the command that is equivalent to undoing add (DeleteCommand)
    @Override
    public Command getUndoCommand() {
        if (isSuccess) {
            return new DeleteCommand(toAdd, isDeleteAllOcurrence);
        } else {
            return null;
        }
    }

    @Override
    public String getUndoCommandWord() {
        return DeleteCommand.COMMAND_WORD + COMMAND_SUFFIX;
    }

    @Override
    public String getRedoCommandWord() {
        return COMMAND_WORD + COMMAND_SUFFIX;
    }
}
