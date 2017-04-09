//@@author A0143409J
package seedu.address.logic.commands;

import java.util.Set;

/**
 * Lists all finished tasks in Dueue or in a specified list.
 */
public class ListFinishedCommand extends ListCommand {

    public static final String MESSAGE_LIST_SUCCESS = "Finished tasks are listed";

    protected Set<String> keywords;

    /**
     * Create a ListFinishedCommand using a set of keywords.
     *
     * @param keywords A set of keywords.
     */
    public ListFinishedCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        assert keywords != null;
        if (keywords.isEmpty()) {
            model.updateFilteredListToShowAllFinishedTasks();
            LOGGER.info(getClass() + " listed all finished tasks");
            return new CommandResult(MESSAGE_LIST_SUCCESS);
        } else if (model.isListExist(keywords)) {
            highlightCurrentTagName(keywords.toString());
            model.updateFilteredTaskListGivenListNameFinished(keywords);
            LOGGER.info(getClass() + " listed all finished tasks in the given lists");
            return new CommandResult(CommandFormatter.listFormatter(MESSAGE_LIST_SUCCESS, keywords));
        } else {
            LOGGER.info(getClass() + " all the listnames given are not found");
            return new CommandResult(MESSAGE_LIST_DOES_NOT_EXIST);
        }
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
