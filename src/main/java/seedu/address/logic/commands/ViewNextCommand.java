//@@author A0147996E
package seedu.address.logic.commands;

import java.util.Calendar;
import java.util.TimeZone;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.TaskDate;

/**
 * Lists all unfinished tasks due by a specified date in Dueue.
 */
public class ViewNextCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "View all tasks in the next %s days\n";
    public static final String MESSAGE_SUCCESS_TODAY = "View all tasks due today\n";
    public static final String MESSAGE_SUCCESS_TMR = "View all tasks due by tomorrow\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List unfinished tasks due by "
            + "the specified date and displays them as a list with index numbers.\n"
            + "Parameters: [next]/[number of days from today/date]\n"
            + "Example: " + COMMAND_WORD + " next/Monday";
    private final String numberOfDays;

    /**
     * Create ViewNextCommand with the number of days from today
     *
     * @param numberDays A integer for number of days from today
     */
    public ViewNextCommand(int numberDays) {
        numberOfDays = String.valueOf(numberDays);
    }

    /**
     * Create ViewNextCommand with a specific date
     *
     * @param date A TaskDate object for the specific date
     * @throws IllegalValueException if today's date cannot be converted into a TaskDate
     */
    public ViewNextCommand(TaskDate date) throws IllegalValueException {
        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));
        TaskDate todayDate = new TaskDate(TaskDate.getDateString(today));
        numberOfDays = String.valueOf(date.compareToDay(todayDate));
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListGivenDaysToDueBy(numberOfDays);
        LOGGER.info(getClass() + " listed all unfinished tasks by " + numberOfDays);
        String messageDisplay = CommandFormatter.viewCommandFeedBackMessageFormatter(
                numberOfDays, MESSAGE_SUCCESS, MESSAGE_SUCCESS_TODAY, MESSAGE_SUCCESS_TMR);
        return new CommandResult(messageDisplay);
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
