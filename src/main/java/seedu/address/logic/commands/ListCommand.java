package seedu.address.logic.commands;


/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks as per the parameters"
            + "the specified listname/list index and displays them as a list with index numbers.\n"
            + "Parameters: [LISTNAME/LISTINDEX]\n"
            + "Example: " + COMMAND_WORD + " CS2103";

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
