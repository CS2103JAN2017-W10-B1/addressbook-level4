//@@author A0138474X
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAVOURITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREQUENCY;
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

    private static AddCommandParser theOne;

    private AddCommandParser() {
    }

    public static AddCommandParser getInstance() {
        if (theOne == null) {
            theOne = new AddCommandParser();
        }
        return theOne;
    }
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public static Command parse(String args) {
        // check whether the user did not enter an empty add command
        if (args == null) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        // initialize the ArgumentTokenizer to read in different detail field of the task to be added
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_TIME, PREFIX_TAG, PREFIX_DESCRIPTION,
                        PREFIX_VENUE, PREFIX_PRIORITY, PREFIX_FAVOURITE, PREFIX_START,
                        PREFIX_STARTTIME, PREFIX_FREQUENCY);
        argsTokenizer.tokenize(args);
        try {
            // get the different detail field of the task to be added
            String name = argsTokenizer.getPreamble().get();
            String date = checkString(argsTokenizer.getValue(PREFIX_DATE));
            String startDate = checkString(argsTokenizer.getValue(PREFIX_START));
            String time = checkString(argsTokenizer.getValue(PREFIX_TIME));
            String startTime = checkString(argsTokenizer.getValue(PREFIX_STARTTIME));
            String tag = checkString(argsTokenizer.getValue(PREFIX_TAG));
            String description = checkString(argsTokenizer.getValue(PREFIX_DESCRIPTION));
            String venue = checkString(argsTokenizer.getValue(PREFIX_VENUE));
            String priority = checkString(argsTokenizer.getValue(PREFIX_PRIORITY));
            String frequency = checkString(argsTokenizer.getValue(PREFIX_FREQUENCY));
            // check if the user wants to a a special task
            boolean isEvent = checkEvent(startDate, startTime);
            boolean isFavourite = checkFav(argsTokenizer.getValue(PREFIX_FAVOURITE));
            boolean isRecurring = !frequency.isEmpty();
            // pass the detail field of the task to be added to the add command
            return new AddCommand(name, date, startDate, time, startTime, tag,
                    description, venue, priority, frequency, isFavourite, isEvent, isRecurring);
        } catch (NoSuchElementException ive) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    // check if the user wants to add an event by checking if either the start or the startTime is filled
    private static boolean checkEvent(String start, String startTime) {
        return !start.isEmpty() || !startTime.isEmpty();
    }

    // check if the user add in a favorite task by checking whether the favorite(*f) indication is present
    private static boolean checkFav(Optional<String> args) {
        return args.isPresent();
    }

    // check if the string is present if not give it an empty string
    private static String checkString(Optional<String> args) {
        return args.orElse("");
    }

}
