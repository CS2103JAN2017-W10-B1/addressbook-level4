//@@author A0143409J
package seedu.address.logic.commands;

import java.util.Calendar;
import java.util.TimeZone;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.TaskDate;

/**
 * Lists all unfinished tasks due by a specified date in Dueue.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "View all tasks in the next %s days\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List unfinished tasks due by "
            + "the specified date and displays them as a list with index numbers.\n"
            + "Parameters: [next]/[number of days from today]\n"
            + "Example: " + COMMAND_WORD + " next/10";

    private final String numberOfDays;

    public ViewCommand(int numberDays) {
        numberOfDays = String.valueOf(numberDays);
    }

    public ViewCommand(TaskDate date) throws IllegalValueException {
        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"));
        TaskDate todayDate = new TaskDate(TaskDate.getDateString(today));
        numberOfDays = String.valueOf(date.compareToDay(todayDate));
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListGivenDaysToDueBy(numberOfDays);
        String messageDisplay = String.format(MESSAGE_SUCCESS, numberOfDays);
        LOGGER.info(getClass() + " listed all unfinished tasks by " + numberOfDays);
        return new CommandResult(messageDisplay);
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
