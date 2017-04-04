//@@author A0143409J
package seedu.address.logic.commands;

import java.util.Calendar;
import java.util.TimeZone;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.TaskDate;

/**
 * List tasks due on a specified date in Dueue.
 */
public class ViewOnCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "View all tasks due on %s days later\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks due on "
            + "the specified date and displays them as a list with index numbers.\n"
            + "Parameters: [on/[number of days from today]\n"
            + "Example: " + COMMAND_WORD + " on/10";

    private final String numberOfDays;

    public ViewOnCommand(int numberDays) {
        numberOfDays = String.valueOf(numberDays);
    }

    public ViewOnCommand(TaskDate date) throws IllegalValueException {
        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));
        TaskDate todayDate = new TaskDate(TaskDate.getDateString(today));
        numberOfDays = String.valueOf(date.compareToDay(todayDate));
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListGivenDaysToDueOn(numberOfDays);
        LOGGER.info(getClass() + " listed all tasks on " + numberOfDays);
        String messageDisplay = String.format(MESSAGE_SUCCESS, numberOfDays);
        return new CommandResult(messageDisplay);
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
