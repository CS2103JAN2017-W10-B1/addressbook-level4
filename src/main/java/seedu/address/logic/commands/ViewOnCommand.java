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
    public static final String MESSAGE_SUCCESS_TODAY = "View all tasks due today\n";
    public static final String MESSAGE_SUCCESS_TMR = "View all tasks due on tomorrow\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks due on "
            + "the specified date and displays them as a list with index numbers.\n"
            + "Parameters: on/[number of days from today/date]\n"
            + "Example: " + COMMAND_WORD + " on/tmr\n";
    public static final String MESSAGE_NONNEGATIVE = "The number of days in the future cannot be negative.\n";

    private final String numberOfDays;

    /**
     * Create ViewOnCommand with the number of days from today
     *
     * @param numberDays A integer for number of days from today
     */
    public ViewOnCommand(int numberDays) throws IllegalValueException {
        if (numberDays < 0) {
            throw new IllegalValueException(MESSAGE_NONNEGATIVE);
        }
        numberOfDays = String.valueOf(numberDays);
    }

    /**
     * Create ViewOnCommand with a specific date
     *
     * @param date A TaskDate object for the specific date
     * @throws IllegalValueException if today's date cannot be converted into a TaskDate
     */
    public ViewOnCommand(TaskDate date) throws IllegalValueException {
        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));
        TaskDate todayDate = new TaskDate(TaskDate.getDateString(today));
        int numberDays = date.compareToDay(todayDate);
        if (numberDays < 0) {
            throw new IllegalValueException(MESSAGE_NONNEGATIVE);
        }
        numberOfDays = String.valueOf(date.compareToDay(todayDate));
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListGivenDaysToDueOn(numberOfDays);
        LOGGER.info(getClass() + " listed all tasks on " + numberOfDays);
        String messageDisplay = CommandFormatter.viewCommandFeedBackMessageFormatter(
                numberOfDays, MESSAGE_SUCCESS, MESSAGE_SUCCESS_TODAY, MESSAGE_SUCCESS_TMR);
        return new CommandResult(messageDisplay);
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
