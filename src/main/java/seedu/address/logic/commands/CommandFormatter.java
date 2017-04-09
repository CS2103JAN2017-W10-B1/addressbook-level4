//@@author A0143409J
/*
 * A formatter class doing formatting for all commands output messages
 */
package seedu.address.logic.commands;

import java.util.Iterator;
import java.util.Set;

/*
 *  A formatter class to help other command classes to format strings.
 */
public class CommandFormatter {

    private static final String LIST_SEPARATOR = ", ";
    private static final String LAST_TASK = "last task";
    private static final String UNDO_FRIENDLY = "%s\nYou can undo the %s by typing 'undo'";

    private CommandFormatter() {};

    /**
     * Create output message for all types of list commands
     *
     * @param message The output message before "in list"
     * @param keywords A set of listnames displayed
     * @return A string of message for the list commands
     */
    public static String listFormatter(String message, Set<String> keywords) {
        String formatted = message + " in list ";
        for (Iterator<String> i = keywords.iterator(); i.hasNext(); ) {
            formatted += i.next();
            if (i.hasNext()) {
                formatted += LIST_SEPARATOR;
            }
        }
        return formatted;
    }

    /**
     * Create output message for the AbleUndoCommands
     * Give the interactive feedback to remind the user they can use undo
     *
     * @param message Initial output message of the command
     * @param commandWord The command word + command
     * @return Output message for display after AbleUndoCommands
     */
    public static String undoFormatter(String message, String commandWord) {
        return String.format(UNDO_FRIENDLY, message, commandWord);
    }

    /**
     * Create the output message for undo commands
     * Give interactive feedback to remind the user they have undone the command
     * @param message The initial message output to the user
     * @param commandWord The command undone
     * @return Output message displayed after undoing command
     */
    public static String undoMessageFormatter(String message, String commandWord) {
        return message.replace(LAST_TASK, commandWord);
    }

    /**
     * Create the output message for undo commands
     * Give interactive feedback to remind the user they have undone the command
     * @param message The initial message output to the user
     * @param command The AbleUndoCommand undone
     * @return Output message displayed after undoing command
     */
    public static String undoMessageFormatter(String message, AbleUndoCommand command) {
        String commandWord = command.getUndoCommandWord();
        return undoMessageFormatter(message, commandWord);
    }

    /**
     * Create the output message for viewOn or viewBy commands
     * Give interactive feedback for today/tomorrow
     *
     * @param numberOfDays The number of days from today
     * @param messageViewSuccess The message when it is not today/tomorrow
     * @param messageViewSuccessToday The message for today
     * @param messageViewSuccessTmr The message for tomorrow
     * @return Output message displayed after executing view
     */
    public static String viewCommandFeedBackMessageFormatter(
            String numberOfDays, String messageViewSuccess,
            String messageViewSuccessToday, String messageViewSuccessTmr) {
        if ("0".equals(numberOfDays)) {
            return String.format(messageViewSuccessToday, numberOfDays);
        } else if ("1".equals(numberOfDays)) {
            return String.format(messageViewSuccessTmr, numberOfDays);
        } else {
            return  String.format(messageViewSuccess, numberOfDays);
        }
    }

    /**
     * Create a list of parameters by chopping up a string
     * for the ViewCommandParser
     *
     * @param args A {@code string} containing all the parameters connected by slash
     * @return A list of {@code String} parameters
     */
    public static String[] viewCommandParserFormatter(String args) {
        String argument = args.trim();
        String[] parameters = argument.split("/", 2);
        return parameters;
    }
}
