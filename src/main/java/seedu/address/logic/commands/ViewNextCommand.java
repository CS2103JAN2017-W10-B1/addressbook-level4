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

    public ViewNextCommand(int numberDays) throws IllegalValueException {
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
        String messageDisplay = feedBackMessageFormatter();
        LOGGER.info(getClass() + " listed all unfinished tasks by " + numberOfDays);
        return new CommandResult(messageDisplay);
    }

    public String feedBackMessageFormatter() {
        if (numberOfDays.equals("0")) {
            return String.format(MESSAGE_SUCCESS_TODAY, numberOfDays);
        } else if (numberOfDays.equals("1")) {
            return String.format(MESSAGE_SUCCESS_TMR, numberOfDays);
        } else {
            return  String.format(MESSAGE_SUCCESS, numberOfDays);
        }
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
