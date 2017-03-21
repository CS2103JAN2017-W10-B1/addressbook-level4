//@@author A0143409J
package seedu.address.logic.commands;

import java.util.Iterator;
import java.util.Set;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks as per the parameters\n"
            + "the specified listname/list index and displays them as a list with index numbers.\n"
            + "Parameters: [LISTNAME/LISTINDEX]\n"
            + "Example: " + COMMAND_WORD + " CS2103";

    private static final String LIST_SEPARATOR = ", ";

    private final Set<String> keywords;

    public ViewCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    public ViewCommand() {
        this.keywords = null;
    }

    @Override
    public CommandResult execute() {
        if (keywords == null) {
            model.updateFilteredListToShowAllTasks();
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            model.updateFilteredTaskListGivenListName(keywords);
            return new CommandResult(formatter(keywords));
        }
    }

    private String formatter(Set<String> keywords) {
        String formatted = MESSAGE_SUCCESS + " in list ";
        for (Iterator<String> it = keywords.iterator(); it.hasNext(); ) {
            formatted += it.next();
            if (it.hasNext()) {
                formatted += LIST_SEPARATOR;
            }
        }
        return formatted;
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
