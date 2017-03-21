//@@author A0143409J
package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;

/**
 * Lists all persons in the address book to the user.
 */
public class AddListCommand extends Command {

    public static final String COMMAND_WORD = "addlist";

    public static final String MESSAGE_SUCCESS = "Successfully added the list with name";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add list with list name as per the parameter\n"
            + "Parameter: [LISTNAME]\n"
            + "Example: " + COMMAND_WORD + " CS2103";

    private static final String MESSAGE_NULL_LISTNAME = "The listname should not be null.";

    private static final String MESSAGE_LISTNAME_TAKEN = "Ths listname has already been used.";

    private static final String MESSAGE_INVALID_LISTNAME = "Ths listname is not in the valid format.";

    private final String listName;

    public AddListCommand(String listName) {
        this.listName = listName;
    }

    @Override
    public CommandResult execute() {
        if (listName == null) {
            model.updateFilteredListToShowAllTasks();
            return new CommandResult(String.format(MESSAGE_NULL_LISTNAME, MESSAGE_USAGE));
        } else {
            Tag tag;
		    try {
			    tag = new Tag(listName);
		    } catch (IllegalValueException e) {
			    return new CommandResult(MESSAGE_INVALID_LISTNAME);
		    }
            try {
                model.addList(tag);
            } catch (DuplicateTagException e) {
			    return new CommandResult(MESSAGE_LISTNAME_TAKEN);
		    }
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
