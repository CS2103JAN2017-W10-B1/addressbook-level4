//@@author A0138474X
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AbleUndoCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FinishCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScrollToCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewNextCommand;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput, Stack<AbleUndoCommand> commandList,
            Stack<AbleUndoCommand> undoCommandList) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand(commandList, undoCommandList);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand(undoCommandList);

        case AddCommand.COMMAND_WORD:
            undoCommandList.clear();
            return AddCommandParser.getInstance().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case EditCommand.COMMAND_WORD:
            undoCommandList.clear();
            return EditCommandParser.getInstance().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            undoCommandList.clear();
            return DeleteCommandParser.getInstance().parse(arguments);

        case FinishCommand.COMMAND_WORD:
            undoCommandList.clear();
            return FinishCommandParser.getInstance().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            undoCommandList.clear();
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return FindCommandParser.getInstance().parse(arguments);

        case LoadCommand.COMMAND_WORD:
            return LoadCommandParser.getInstance().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return ListCommandParser.getInstance().parse(arguments);

        case ScrollToCommand.COMMAND_WORD:
            return ScrollToCommandParser.getInstance().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return HelpCommandParser.getInstance().parse(arguments);

        case ViewNextCommand.COMMAND_WORD:
            return ViewCommandParser.getInstance().parse(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
