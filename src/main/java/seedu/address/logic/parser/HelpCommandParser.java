//@@author A0143409J
package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FinishCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.ScrollToCommand;
import seedu.address.logic.commands.ViewNextCommand;
import seedu.address.logic.commands.ViewOnCommand;

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser {

    private static HelpCommandParser theOne;

    private HelpCommandParser() {
    }

    public static HelpCommandParser getInstance() {
        if (theOne == null) {
            theOne = new HelpCommandParser();
        }
        return theOne;
    }

    /**
     * Parses the given {String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     */
    public Command parse(String args) {
        if (args == null) {
            return new HelpCommand();
        }
        // keywords delimited by whitespace
        final String commandWord = args.trim();
        final String helpMessage = parseCommand(commandWord);
        if (helpMessage == null) {
            return new HelpCommand();
        } else {
            return new HelpCommand(helpMessage);
        }
    }

    /**
     * Parses the given {String} of command in the context of the HelpCommand
     * and returns the help message of the corresponding command.
     *
     * Considering to make it more generic together with the similar part in Parser.
     */
    private static String parseCommand(String commandWord) {
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return AddCommand.MESSAGE_USAGE;

        case EditCommand.COMMAND_WORD:
            return EditCommand.MESSAGE_USAGE;

        case DeleteCommand.COMMAND_WORD:
            return DeleteCommand.MESSAGE_USAGE;

        case ClearCommand.COMMAND_WORD:
            return ClearCommand.MESSAGE_USAGE;

        case FindCommand.COMMAND_WORD:
            return FindCommand.MESSAGE_USAGE;

        case ListCommand.COMMAND_WORD:
            return ListCommand.MESSAGE_USAGE;

        case LoadCommand.COMMAND_WORD:
            return LoadCommand.MESSAGE_USAGE;

        case ExitCommand.COMMAND_WORD:
            return ExitCommand.MESSAGE_USAGE;

        case HelpCommand.COMMAND_WORD:
            return HelpCommand.MESSAGE_USAGE;

        case FinishCommand.COMMAND_WORD:
            return FinishCommand.MESSAGE_USAGE;

        case ScrollToCommand.COMMAND_WORD:
            return ScrollToCommand.MESSAGE_USAGE;

        case ViewNextCommand.COMMAND_WORD:
            return ViewNextCommand.MESSAGE_USAGE + "\n" + ViewOnCommand.MESSAGE_USAGE;

        default:
            return null;
        }
    }
}
