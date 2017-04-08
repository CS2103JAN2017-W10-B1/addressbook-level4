//@@author A0143409J
package seedu.address.logic.commands;

import static seedu.address.logic.commands.ListCommand.MESSAGE_LIST_DOES_NOT_EXIST;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToTagListRequestEvent;
import seedu.address.model.TaskManager;

/**
 * Lists all finished tasks in Dueue or in a specified list.
 */
public class ListFinishedCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_LIST_FINISHED_SUCCESS = "Finished tasks are listed! Well done!\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks as per the parameters\n"
            + "the specified listname and displays them as a list with index numbers.\n"
            + "Parameters: [all/favorite/finished] [LIST_NAME]\n"
            + "Example: " + COMMAND_WORD + "\n"
            + COMMAND_WORD + " all study\n"
            + COMMAND_WORD + " finished\n"
            + COMMAND_WORD + "study work\n";

    private final Set<String> keywords;

    public ListFinishedCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    public ListFinishedCommand() {
        keywords = new HashSet<String>();
    }

    @Override
    public CommandResult execute() {
        assert keywords != null;
        if (keywords.isEmpty()) {
            model.updateFilteredListToShowAllFinishedTasks();
            LOGGER.info(getClass() + " listed all finished tasks");
            return new CommandResult(MESSAGE_LIST_FINISHED_SUCCESS);
        } else if (model.isListExist(keywords)) {
            highlightCurrentTagName(keywords);
            model.updateFilteredTaskListGivenListNameFinished(keywords);
            LOGGER.info(getClass() + " listed all finished tasks in the given lists");
            return new CommandResult(CommandFormatter.listFormatter(MESSAGE_LIST_FINISHED_SUCCESS, keywords));
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
