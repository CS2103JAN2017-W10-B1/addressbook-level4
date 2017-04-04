//@@author A0143409J
package seedu.address.logic.commands;

import java.util.Calendar;
import java.util.TimeZone;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.TaskDate;

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "View all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks due by "
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
        LOGGER.info(getClass() + " listed all tasks by " + numberOfDays);
        String messageDisplay = MESSAGE_SUCCESS + " in the next " + numberOfDays + " days\n";
        return new CommandResult(messageDisplay);
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
