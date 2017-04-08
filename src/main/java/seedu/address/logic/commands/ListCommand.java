//@@author A0138474X
package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToTagListRequestEvent;
import seedu.address.model.TaskManager;

/**
 * Lists all unfinished tasks in Dueue or in a specified list.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_LIST_SUCCESS = "Unfinished tasks are listed!";
    public static final String MESSAGE_LIST_DOES_NOT_EXIST = "Oops, given list name does not exist.\n"
            + "You may want to refer to the list names in the left column.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks as per the parameters\n"
            + "the specified listname and displays them as a list with index numbers.\n"
            + "Parameters: [all/favorite/finished] [LIST_NAME]\n"
            + "Example: " + COMMAND_WORD + "\n"
            + COMMAND_WORD + " all study\n"
            + COMMAND_WORD + " finished\n";

    private final Set<String> keywords;

    public ListCommand(Set<String> keywords) {
        assert !keywords.isEmpty();
        this.keywords = keywords;
    }

    public ListCommand() {
        keywords = new HashSet<String>();
    }

    @Override
    public CommandResult execute() {
        assert keywords != null;
        if (keywords.isEmpty()) {
            model.updateFilteredListToShowAllUnfinishedTasks();
            LOGGER.info(getClass() + " listed all unfinished tasks");
            return new CommandResult(MESSAGE_LIST_SUCCESS);
        } else if (!model.isListExist(keywords)) {
            LOGGER.info(getClass() + " all the listnames given are not found");
            return new CommandResult(MESSAGE_LIST_DOES_NOT_EXIST);
        } else {
            highlightCurrentTagName(keywords);
            model.updateFilteredTaskListGivenListName(keywords);
            LOGGER.info(getClass() + " listed all unfinished tasks in the given lists");
            return new CommandResult(CommandFormatter.listFormatter(MESSAGE_LIST_SUCCESS, keywords));
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

  //@@author A0138474X
    @Override
    public boolean isUndoable() {
        return false;
    }
}
