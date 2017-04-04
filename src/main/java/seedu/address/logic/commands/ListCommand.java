//@@author A0138474X
package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

/**
 * Lists all unfinished tasks in Dueue or in a specified list.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_LIST_SUCCESS = "Listed unfinished tasks";
    public static final String MESSAGE_LIST_DOES_NOT_EXIST = "Given list name does not exist";

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
        this.keywords = new HashSet<String>();
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
            model.updateFilteredTaskListGivenListName(keywords);
            LOGGER.info(getClass() + " listed all unfinished tasks in the given lists");
            return new CommandResult(CommandFormatter.listFormatter(MESSAGE_LIST_SUCCESS, keywords));
        }
    }

  //@@author A0138474X
    @Override
    public boolean isUndoable() {
        return false;
    }
}
