//@@author A0138474X
package seedu.address.logic.commands;

import java.util.Iterator;
import java.util.Set;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.tag.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String LIST_ALL = "all";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_LIST_DOES_NOT_EXIST= "Given list name does not exist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks as per the parameters\n"
            + "the specified listname/list index and displays them as a list with index numbers.\n"
            + "Parameters: [LISTNAME/LISTINDEX]\n"
            + "Example: " + COMMAND_WORD + " study work";

    private static final String LIST_SEPARATOR = ", ";

    private final Set<String> keywords;

    public ListCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    public ListCommand() {
        this.keywords = null;
    }

    @Override
    public CommandResult execute() {
        if (keywords == null) {
            model.updateFilteredListToShowAllTasks();
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (keywords.contains(LIST_ALL)) {
            model.updateFilteredListToShowAllTasksAll();
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (!isKeywordMatchingTaglist()) {
            return new CommandResult(MESSAGE_LIST_DOES_NOT_EXIST);
        } else {
            model.updateFilteredTaskListGivenListName(keywords);
            return new CommandResult(formatter(keywords));
        }
    }

//@@author A0147996E
    private boolean isKeywordMatchingTaglist () {
        boolean hasMatch = false;
        UnmodifiableObservableList<Tag> tagList = UniqueTagList.asObservableList();
        for(String keyword : keywords) {
            for(Tag tag : tagList) {
                if(tag.getName().equalsIgnoreCase(keyword)) {
                    hasMatch = true;
                }
            }
        } return hasMatch;
    }
//@@author

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
