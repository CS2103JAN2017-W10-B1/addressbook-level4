//@@author A0147996E-reused
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ScrollToCommand;

/**
 * Parses input arguments and creates a new ScrollToCommand object
 */
public class ScrollToCommandParser {

    private static ScrollToCommandParser theOne;

    private ScrollToCommandParser() {
    }

    public static ScrollToCommandParser getInstance() {
        if (theOne == null) {
            theOne = new ScrollToCommandParser();
        }
        return theOne;
    }
    /**
     * Parses the given {@code String} of arguments in the context of the ScrollToCommand
     * and returns an ScrollToCommand object for execution.
     */
    public Command parse(String args) {
        assert args != null;
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScrollToCommand.MESSAGE_USAGE));
        }
        return new ScrollToCommand(index.get());
    }

}
