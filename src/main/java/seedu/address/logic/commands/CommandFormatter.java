//@@author A0143409J
/*
 * A formatter class doing formatting for all commands output messages
 */
package seedu.address.logic.commands;

import java.util.Iterator;
import java.util.Set;

public class CommandFormatter {

    private static final String LIST_SEPARATOR = ", ";
    private static final String LAST_TASK = "last task";
    private static final String UNDO_FRIENDLY = "%s\nYou can undo the %s by typing 'undo'";

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
        return String.format(UNDO_FRIENDLY, message, commandWord);
    }

    public static String undoMessageFormatter(String message, String commandWord) {
        return message.replace(LAST_TASK, commandWord);
    }

    public static String undoMessageFormatter(String message, AbleUndoCommand command) {
        String commandWord = command.getUndoCommandWord();
        return undoMessageFormatter(message, commandWord);
    }
}
