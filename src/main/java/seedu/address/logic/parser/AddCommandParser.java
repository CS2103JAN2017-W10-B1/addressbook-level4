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

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

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
        if (args == null) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_TIME, PREFIX_TAG, PREFIX_DESCRIPTION,
                        PREFIX_VENUE, PREFIX_PRIORITY, PREFIX_FAVOURITE, PREFIX_START,
                        PREFIX_STARTTIME, PREFIX_FREQUENCY);
        argsTokenizer.tokenize(args);
        try {
            String name = argsTokenizer.getPreamble().get();
            String date = null, startDate = null, time = null, startTime = null,
                    tag = null, description = null, venue = null, priority = null, frequency = null;
            List<String> list = Arrays.asList(date, startDate, time, startTime,
                    tag, description, venue, priority, frequency);
            List<Prefix> prefixList = Arrays.asList(PREFIX_DATE, PREFIX_START,
                    PREFIX_TIME, PREFIX_STARTTIME, PREFIX_TAG, PREFIX_DESCRIPTION,
                    PREFIX_VENUE, PREFIX_VENUE, PREFIX_FREQUENCY);
            list.forEach(element -> element =
                    checkString(argsTokenizer.getValue(prefixList.get(list.indexOf(element)))));
            stringFactory(argsTokenizer, prefixList, date, startDate,
                    time, startTime, tag, description, venue, priority, frequency);
            boolean isEvent = checkStart(startDate) || checkStart(startTime);
            boolean isFavourite = checkPresent(argsTokenizer.getValue(PREFIX_FAVOURITE));
            boolean isRecurring = !frequency.isEmpty();
            return new AddCommand(name, date, startDate, time, startTime, tag,
                    description, venue, priority, frequency, isFavourite, isEvent, isRecurring);
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

    private static void stringFactory(ArgumentTokenizer argsTokenizer, List<Prefix> prefixList, String...strings) {
        List<String> stringList = Arrays.asList(strings);
        for (String string : strings) {
            string = checkString(argsTokenizer.getValue(prefixList.get(stringList.indexOf(string))));
        }
    }
}
