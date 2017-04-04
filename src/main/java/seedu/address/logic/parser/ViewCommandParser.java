//@@author A0143409J
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewOnCommand;
import seedu.address.model.task.TaskDate;

/**
 * Parses input arguments and creates a new ViewCommand/ViewOnCommand object
 */
public class ViewCommandParser {

    private static ViewCommandParser theOne;

    private ViewCommandParser() {}

    public static ViewCommandParser getInstance() {
        if (theOne == null) {
            theOne = new ViewCommandParser();
        }
        return theOne;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     */
    public static Command parse(String args) {
        if (args == null) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        // if matching the format for view next n days, show all the tasks due within the next n days
        String[] parameters = formatter(args);
        if (parameters[0].equals("next")) {
            try {
                return new ViewCommand(Integer.valueOf(parameters[1]));
            } catch (NumberFormatException nfe) {
                try {
                    TaskDate date = new TaskDate(parameters[1]);
                    return new ViewCommand(date);
                } catch (IllegalValueException e) {
                    return new IncorrectCommand(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
                }
            }
        }

        // if matching the format for view due on date, show all the tasks due on that day
        if (parameters[0].equals("on")) {
            try {
                return new ViewOnCommand(Integer.valueOf(parameters[1]));
            } catch (NumberFormatException nfe) {
                try {
                    TaskDate date = new TaskDate(parameters[1]);
                    return new ViewOnCommand(date);
                } catch (IllegalValueException e) {
                    return new IncorrectCommand(String.format(
                            MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
                }
            }
        }
        // if not matching any format
        return new IncorrectCommand(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    public static String[] formatter(String args) {
        String argument = args.trim();
        String[] parameters = argument.split("/", 2);
        return parameters;
    }
}
