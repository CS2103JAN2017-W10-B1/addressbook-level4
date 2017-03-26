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
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ViewCommand;

public class HelpCommandParser {

    /**
     * Parses the given {String} of arguments in the context of the HelpCommand
     * and returns a HelpCommand object for execution.
     */
    public static Command parse(String args) {
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

        case SelectCommand.COMMAND_WORD:
            return SelectCommand.MESSAGE_USAGE;

        case DeleteCommand.COMMAND_WORD:
            return DeleteCommand.MESSAGE_USAGE;

        case ClearCommand.COMMAND_WORD:
            return ClearCommand.MESSAGE_USAGE;

        case FindCommand.COMMAND_WORD:
            return FindCommand.MESSAGE_USAGE;

        case ListCommand.COMMAND_WORD:
            return ListCommand.MESSAGE_USAGE;

        case ExitCommand.COMMAND_WORD:
            return ExitCommand.MESSAGE_USAGE;

        case HelpCommand.COMMAND_WORD:
            return HelpCommand.MESSAGE_USAGE;

        case FinishCommand.COMMAND_WORD:
            return FinishCommand.MESSAGE_USAGE;
            
        case ViewCommand.COMMAND_WORD:
            return ViewCommand.MESSAGE_USAGE;

        default:
            return null;
        }
    }
}
