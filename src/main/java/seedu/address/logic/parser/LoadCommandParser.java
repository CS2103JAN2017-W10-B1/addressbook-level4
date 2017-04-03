//@@author A0147996E

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Parses input arguments and creates a new LoadCommand object
 */
public class LoadCommandParser {
    private static final String DIRECTORY_REGEX = ".*";

    /**
     * Parses the given {@code String} of arguments in the context of the LoadCommand
     * and returns an LoadCommand object for execution.
     * @throws CommandException
     */
    public static Command parse(String args) {
        args = args.trim();
        if (args == null) {
            return new IncorrectCommand(LoadCommand.MESSAGE_DIRECTORY_NOT_GIVEN);
        } else if (args.matches(DIRECTORY_REGEX)) {
            return new LoadCommand(args);
        } else {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE));
        }
    }
}
