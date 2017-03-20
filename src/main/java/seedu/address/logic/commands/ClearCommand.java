package seedu.address.logic.commands;

import seedu.address.model.TaskManager;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Dueue has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clear all tasks in Dueue \n"
            + "Parameters: Nil\n"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }


    @Override
    public boolean isUndoable() {
        return false;
    }
}
