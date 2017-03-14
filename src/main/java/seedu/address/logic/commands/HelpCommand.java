package seedu.address.logic.commands;


import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private String USAGE_MESSAGE = null;

    /**
     * Creates a HelpCommand using one command .
     */

    public HelpCommand(String helpMessage) {
    	super();
    	USAGE_MESSAGE = helpMessage;
    }

    public HelpCommand() {
    	super();
    }

    @Override
    public CommandResult execute() {
    	if (USAGE_MESSAGE == null){
    		EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
    	} else {
    		return new CommandResult(USAGE_MESSAGE);
    	}
    }
}
