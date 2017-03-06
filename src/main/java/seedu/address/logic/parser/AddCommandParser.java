package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;

import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_NAME, PREFIX_DATE, PREFIX_TIME, PREFIX_TAG, PREFIX_DESCRIPTION, PREFIX_VENUE,PREFIX_PRIORITY);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getValue(PREFIX_NAME).get(),
                    argsTokenizer.getValue(PREFIX_DATE).get(),
                    argsTokenizer.getValue(PREFIX_TIME).get(),
                    argsTokenizer.getValue(PREFIX_TAG).get(),
                    argsTokenizer.getValue(PREFIX_DESCRIPTION).get(),
                    argsTokenizer.getValue(PREFIX_VENUE).get(),
                    argsTokenizer.getValue(PREFIX_PRIORITY).get()
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
