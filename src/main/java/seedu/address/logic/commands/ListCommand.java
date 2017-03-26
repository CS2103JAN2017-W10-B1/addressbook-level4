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
    public static final String LIST_FINISHED = "finished";
    public static final String LIST_FAVORITE = "favorite";

    public static final String MESSAGE_LIST_SUCCESS = "Listed unfinished tasks";
    public static final String MESSAGE_LIST_ALL_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_LIST_FINISHED_SUCCESS = "Listed all finished tasks";
    public static final String MESSAGE_LIST_FAVORITE_SUCCESS = "Listed all favorite tasks";
    public static final String MESSAGE_LIST_DOES_NOT_EXIST= "Given list name does not exist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks as per the parameters\n"
            + "the specified listname and displays them as a list with index numbers.\n"
            + "Parameters: [LISTNAME]\n"
            + "Example: " + COMMAND_WORD + "\n"
            + COMMAND_WORD + " all\n"
            + COMMAND_WORD + " finished\n"
            + COMMAND_WORD + "study work\n";;
    

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
            model.updateFilteredListToShowAllUnfinishedTasks();
            return new CommandResult(MESSAGE_LIST_SUCCESS);
        } else if (keywords.contains(LIST_ALL)) {
            model.updateFilteredListToShowAllTasks();
            return new CommandResult(MESSAGE_LIST_ALL_SUCCESS);
        } else if (keywords.contains(LIST_FINISHED)) {
            model.updateFilteredListToShowAllFinishedTasks();
            return new CommandResult(MESSAGE_LIST_FINISHED_SUCCESS);
        } else if (keywords.contains(LIST_FAVORITE)) {
            model.updateFilteredListToShowAllFavoriteTasks();
            return new CommandResult(MESSAGE_LIST_FAVORITE_SUCCESS);
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
        String formatted = MESSAGE_LIST_ALL_SUCCESS + " in list ";
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
