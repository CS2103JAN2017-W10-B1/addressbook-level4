//@@author generated
package seedu.address.logic.commands;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Please add command keyword after help.\n"
            + "The basic command words are as following:\n"
            + "Add, clear, delete, edit, find, finish, list, scroll, view, undo and redo.\n"
            + "Example: help add";

    private String usageMessage;

    /**
     * Creates a HelpCommand using one command .
     */

    public HelpCommand(String helpMessage) {
        super();
        usageMessage = helpMessage;
    }

    public HelpCommand() {
        super();
    }

    @Override
    public CommandResult execute() {
        if (usageMessage == null) {
            return new CommandResult(SHOWING_HELP_MESSAGE);
        } else {
            return new CommandResult(usageMessage);
        }
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
