//@@author A0143409J
package seedu.address.logic.commands;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "View all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks due by "
            + "the specified date and displays them as a list with index numbers.\n"
            + "Parameters: [next]/[number of days from today]\n"
            + "Example: " + COMMAND_WORD + " next/10";

    private final String messageDisplay;

    private final String numberOfDays;

    public ViewCommand(int numberDays) {
        numberOfDays = String.valueOf(numberDays);
        messageDisplay = MESSAGE_SUCCESS + " in the next " + numberOfDays + " days\n";
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskListGivenDaysToDueBy(numberOfDays);
        LOGGER.info(getClass() + " listed all tasks by " + numberOfDays);
        return new CommandResult(messageDisplay);
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
