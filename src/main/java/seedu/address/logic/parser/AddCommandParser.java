//@@author A0138474X
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private AddCommandParser() {
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public static Command parse(String args) {
        if (args == null) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_TIME, PREFIX_TAG, PREFIX_DESCRIPTION,
                        PREFIX_VENUE, PREFIX_PRIORITY, PREFIX_FAVOURITE, PREFIX_START, PREFIX_STARTTIME);
        argsTokenizer.tokenize(args);
        try {
            String name = argsTokenizer.getPreamble().get();
            String date = checkString(argsTokenizer.getValue(PREFIX_DATE));
            String start = checkString(argsTokenizer.getValue(PREFIX_START));
            String time = checkString(argsTokenizer.getValue(PREFIX_TIME));
            String startTime = checkString(argsTokenizer.getValue(PREFIX_STARTTIME));
            String tag = checkString(argsTokenizer.getValue(PREFIX_TAG));
            String description = checkString(argsTokenizer.getValue(PREFIX_DESCRIPTION));
            String venue = checkString(argsTokenizer.getValue(PREFIX_VENUE));
            String priority = checkString(argsTokenizer.getValue(PREFIX_PRIORITY));
            boolean isEvent = checkStart(start);
            boolean isFavourite = checkPresent(argsTokenizer.getValue(PREFIX_FAVOURITE));
            return new AddCommand(name, date, start, time, startTime, tag,
                    description, venue, priority, isFavourite, isEvent);
        } catch (NoSuchElementException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private static boolean checkStart(String start) {
        return !start.isEmpty();
    }

    private static boolean checkPresent(Optional<String> args) {
        return args.isPresent();
    }

    private static String checkString(Optional<String> args) {
        return args.orElse("");
    }

}
