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
import java.util.Optional;

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
            String name = argsTokenizer.getValue(PREFIX_NAME).get();
            String date = checkString(argsTokenizer.getValue(PREFIX_DATE));
            String time = checkString(argsTokenizer.getValue(PREFIX_TIME));
            String tag = checkString(argsTokenizer.getValue(PREFIX_TAG));
            String description = checkString(argsTokenizer.getValue(PREFIX_DESCRIPTION));
            String venue = checkString(argsTokenizer.getValue(PREFIX_VENUE));
            String priority = checkString(argsTokenizer.getValue(PREFIX_PRIORITY));
            if(name != null){
            	return new AddCommand(name, date, time, tag, description, venue, priority);
            }
            else{
            	return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    private String checkString(Optional<String> args){
    	return args.orElse("");
    }

}
