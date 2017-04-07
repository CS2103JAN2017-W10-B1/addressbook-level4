//@@author A0143409J
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ViewNextCommand;
import seedu.address.logic.commands.ViewOnCommand;
import seedu.address.model.task.TaskDate;

/**
 * Parses input arguments and creates a new ViewNextCommand/ViewOnCommand object
 */
public class ViewCommandParser {

    private static ViewCommandParser theOne;
    public static final String MESSAGE_NONNEGATIVE = "The number of days in the future cannot be negative.\n";

    private ViewCommandParser() {}

    public static ViewCommandParser getInstance() {
        if (theOne == null) {
            theOne = new ViewCommandParser();
        }
        return theOne;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ViewNextCommand
     * and returns an ViewNextCommand object for execution.
     * @throws IllegalValueException
     */
    public static Command parse(String args) {
        if (args == null || args.equals("")) {
            return parseNoParamGiven();
        }

        String[] parameters = formatter(args);
        if (parameters[0].equals("next") || parameters[0].equals("by")) {
            return parseViewNext(parameters);
        } else if (parameters[0].equals("on")) {
            return parseViewOn(parameters);
        } else {
            return new IncorrectCommand(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewNextCommand.MESSAGE_USAGE));
        }
    }

    private static Command parseNoParamGiven() {
        try {
            return new ViewNextCommand(0);
        } catch (IllegalValueException e) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ViewNextCommand.MESSAGE_USAGE));
        }
    }

    private static Command parseViewNext(String[] parameters) {
        try {
            if (Integer.valueOf(parameters[1]) < 0) {
                return new IncorrectCommand(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NONNEGATIVE));
            } else {
                return new ViewNextCommand(Integer.valueOf(parameters[1]));
            }
        } catch (NumberFormatException nfe) {
            try {
                TaskDate date = new TaskDate(parameters[1]);
                return new ViewNextCommand(date);
            } catch (IllegalValueException e) {
                return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ViewNextCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException e) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ViewNextCommand.MESSAGE_USAGE));
        }
    }

    private static Command parseViewOn(String[] parameters) {
        try {
            if (Integer.valueOf(parameters[1]) < 0) {
                return new IncorrectCommand(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NONNEGATIVE));
            } else {
                return new ViewOnCommand(Integer.valueOf(parameters[1]));
            }
        } catch (NumberFormatException nfe) {
            try {
                TaskDate date = new TaskDate(parameters[1]);
                return new ViewOnCommand(date);
            } catch (IllegalValueException e) {
                return new IncorrectCommand(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, ViewOnCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException e) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, ViewOnCommand.MESSAGE_USAGE));
        }
    }

    public static String[] formatter(String args) {
        String argument = args.trim();
        String[] parameters = argument.split("/", 2);
        return parameters;
    }
}
