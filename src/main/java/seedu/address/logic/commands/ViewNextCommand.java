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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List unfinished tasks due by "
            + "the specified date and displays them as a list with index numbers.\n"
            + "Parameters: [next]/[number of days from today]\n"
            + "Example: " + COMMAND_WORD + " next/10";

    public static final String MESSAGE_NONNEGATIVE = "The number of days in the future cannot be negative.\n";

    private String numberOfDays;

    public void ViewCommand(int numberDays) throws IllegalValueException {
        if (numberDays < 0) {
            throw new IllegalValueException("The number of days in the future cannot be negative.");
        }
        numberOfDays = String.valueOf(numberDays);
    }

    public ViewNextCommand(TaskDate date) throws IllegalValueException {
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
