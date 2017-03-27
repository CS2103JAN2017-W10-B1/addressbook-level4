//@@author A0143409J
package seedu.address.logic.commands;

import java.util.Iterator;
import java.util.Set;

/**
 * Lists all persons in the address book to the user.
 */
public class ListFavoriteCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String LIST_ALL = "all";
    public static final String LIST_FINISHED = "finished";
    public static final String LIST_FAVORITE = "favorite";

    public static final String MESSAGE_LIST_FAVORITE_SUCCESS = "Listed all favorite tasks";
    public static final String MESSAGE_LIST_DOES_NOT_EXIST = "Given list name does not exist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks as per the parameters\n"
            + "the specified listname and displays them as a list with index numbers.\n"
            + "Parameters: [all/favorite/finished] [LIST_NAME]\n"
            + "Example: " + COMMAND_WORD + "\n"
            + COMMAND_WORD + " all study\n"
            + COMMAND_WORD + " finished\n"
            + COMMAND_WORD + "study work\n";

    private static final String LIST_SEPARATOR = ", ";

    private final Set<String> keywords;

    public ListFavoriteCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    public ListFavoriteCommand() {
        this.keywords = null;
    }

    @Override
    public CommandResult execute() {
        if (keywords.isEmpty()) {
            model.updateFilteredListToShowAllFavoriteTasks();
            return new CommandResult(MESSAGE_LIST_FAVORITE_SUCCESS);
        } else if (model.isListExist(keywords)) {
            model.updateFilteredTaskListGivenListNameAllFavorite(keywords);
            return new CommandResult(formatter(MESSAGE_LIST_FAVORITE_SUCCESS, keywords));
        } else {
            return new CommandResult(MESSAGE_LIST_DOES_NOT_EXIST);
        }
    }

    private String formatter(String message, Set<String> keywords) {
        String formatted = message + " in list ";
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
