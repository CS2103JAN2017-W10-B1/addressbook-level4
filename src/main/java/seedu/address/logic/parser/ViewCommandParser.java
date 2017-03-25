package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewOnCommand;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser {

    private ViewCommandParser() {
    }
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     */
    public static Command parse(String args) {
        if (args == null) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        // if matching the format for view next n days, show all the tasks due within the next n days
        String[] parameters = formatter(args);
        if (parameters[0] == "next") {
            try {
                return new ViewCommand(Integer.valueOf(parameters[1]));
            } catch (NumberFormatException nfe) {
                return new IncorrectCommand(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
            }
        }

        // if matching the format for view due on date, show all the tasks due on that day
        if (parameters[0] == "on") {
            try {
                return new ViewOnCommand(Integer.valueOf(parameters[1]));
            } catch (NumberFormatException nfe) {
                return new IncorrectCommand(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
            }
        }

        // if not matching any format
        return new IncorrectCommand(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    private static String[] formatter(String args) {
        String argument = args.trim();
        String[] parameters = argument.split("/");
        return parameters;
    }

}
