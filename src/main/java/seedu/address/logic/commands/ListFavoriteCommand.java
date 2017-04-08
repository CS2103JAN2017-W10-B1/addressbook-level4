//@@author A0143409J
package seedu.address.logic.commands;

import static seedu.address.logic.commands.ListCommand.MESSAGE_LIST_DOES_NOT_EXIST;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToTagListRequestEvent;
import seedu.address.model.TaskManager;

/**
 * Lists all favorite unfinished tasks in Dueue or in a specified list.
 */
public class ListFavoriteCommand extends Command {

    public static final String LIST_ALL = "all";
    public static final String LIST_FINISHED = "finished";
    public static final String LIST_FAVORITE = "favorite";

    public static final String MESSAGE_LIST_FAVORITE_SUCCESS = "Favorite tasks are listed!";

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
        keywords = new HashSet<String>();
    }

    @Override
    public CommandResult execute() {
        assert keywords != null;
        if (keywords.isEmpty()) {
            model.updateFilteredListToShowAllFavoriteTasks();
            LOGGER.info(getClass() + " listed all favorite tasks");
            return new CommandResult(MESSAGE_LIST_FAVORITE_SUCCESS);
        } else if (model.isListExist(keywords)) {
            highlightCurrentTagName(keywords);
            model.updateFilteredTaskListGivenListNameAllFavorite(keywords);
            LOGGER.info(getClass() + " listed all favorite tasks in the given lists");
            return new CommandResult(CommandFormatter.listFormatter(MESSAGE_LIST_FAVORITE_SUCCESS, keywords));
        } else {
            LOGGER.info(getClass() + " all the listnames given are not found");
            return new CommandResult(MESSAGE_LIST_DOES_NOT_EXIST);
        }
    }
  //@@author A0147996E
    /**
     * Highlight the single tag if user requests to filter tasks under a single list.
     * Does not support highlighting multiple list names concurrently.
     * @param keywords
     */
    private void highlightCurrentTagName(Set<String> keywords) {
        int index = TaskManager.getInstance().find(keywords.toString());
        if (index != -1) {
            EventsCenter.getInstance().post(new JumpToTagListRequestEvent(index));
        }
    }
    @Override
    public boolean isUndoable() {
        return false;
    }
}
