//@@author A0143409J
package seedu.address.logic.commands;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Please add command keyword after help.\n"
            + "The basic command words are as follows:\n"
            + "Add, clear, delete, edit, find, finish, list, scroll, view, undo and redo.\n"
            + "Example: help add";

    private String usageMessage;

    /**
     * Create a HelpCommand object with help message for a command
     *
     * @param helpMessage The message for usage of a specific command
     */
    public HelpCommand(String helpMessage) {
        super();
        usageMessage = helpMessage;
    }

    /**
     * Create a HelpComand object with no specific help message
     */
    public HelpCommand() {
        super();
    }

    @Override
    public CommandResult execute() {
        if (usageMessage == null) {
            LOGGER.info(getClass() + " general message shown");
            return new CommandResult(SHOWING_HELP_MESSAGE);
        } else {
            LOGGER.info(getClass() + " usage message for specific command shown");
            return new CommandResult(usageMessage);
        }
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
