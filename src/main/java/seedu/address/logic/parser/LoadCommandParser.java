//@@author A0147996E

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Parses input arguments and creates a new LoadCommand object
 */
public class LoadCommandParser {
    public static final String MESSAGE_ILLEGAL_DATA_TYPE = "File must be in XML format";
    private static LoadCommandParser theOne;

    private LoadCommandParser() {};

    public static LoadCommandParser getInstance() {
        if (theOne == null) {
            theOne = new LoadCommandParser();
        }
        return theOne;
    }
    /**
     * Parses the given {@code String} of arguments in the context of the LoadCommand
     * and returns an LoadCommand object for execution.
     * @throws DataConversionException
     * @throws CommandException
     */
    public Command parse(String args) {
        if (args != null) {
            args = args.trim();
        }
        if (args == null || args.equals("")) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        } else {
            return new LoadCommand(args);
        }
    }
}
