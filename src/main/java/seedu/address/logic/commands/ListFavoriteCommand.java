//@@author A0143409J
package seedu.address.logic.commands;

import java.util.HashSet;
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

    private final Set<String> keywords;

    public ListFavoriteCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    public ListFavoriteCommand() {
        this.keywords = new HashSet<String>();
    }

    @Override
    public CommandResult execute() {
        assert keywords != null;
        if (keywords.isEmpty()) {
            model.updateFilteredListToShowAllFavoriteTasks();
            LOGGER.info("Listed all favorite tasks");
            return new CommandResult(MESSAGE_LIST_FAVORITE_SUCCESS);
        } else if (model.isListExist(keywords)) {
            model.updateFilteredTaskListGivenListNameAllFavorite(keywords);
            LOGGER.info("Listed all favorite tasks in the given lists");
            return new CommandResult(CommandFormatter.listFormatter(MESSAGE_LIST_FAVORITE_SUCCESS, keywords));
        } else {
            LOGGER.info("All the listnames given are not found");
            return new CommandResult(MESSAGE_LIST_DOES_NOT_EXIST);
        }
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
