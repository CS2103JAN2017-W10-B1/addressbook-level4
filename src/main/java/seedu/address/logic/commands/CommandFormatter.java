//@@author A0143409J
/*
 * A formatter class doing formatting for all commands output messages
 */
package seedu.address.logic.commands;

import java.util.Iterator;
import java.util.Set;

public class CommandFormatter {

    private static final String LIST_SEPARATOR = ", ";

    private CommandFormatter() {};

    public static String listFormatter(String message, Set<String> keywords) {
        String formatted = message + " in list ";
        for (Iterator<String> it = keywords.iterator(); it.hasNext(); ) {
            formatted += it.next();
            if (it.hasNext()) {
                formatted += LIST_SEPARATOR;
            }
        }
        return formatted;
    }

    public static String undoFormatter(String message, String commandWord) {
        return message + "\nYou can undo the " + commandWord + " by typing 'undo'";
    }

    public static String undoMessageFormatter(String message, String commandWord) {
        return message.replace("last task", commandWord);
    }

    public static String undoMessageFormatter(String message, AbleUndoCommand command) {
        String commandWord = command.getUndoCommandWord() + " command";
        return undoMessageFormatter(message, commandWord);
    }
}
