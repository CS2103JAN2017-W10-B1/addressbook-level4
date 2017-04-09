//@@author generated
package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.JumpToTagListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    protected static final Logger LOGGER = LogsCenter.getLogger(MainApp.class);

    protected Model model;

    public static final String MESSAGE_USAGE = "";

    public static final String COMMAND_WORD = "";


    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks found.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskFoundShownSummary(int displaySize) {
        String resultMsg = String.format(Messages.MESSAGE_TASKS_FOUND_OVERVIEW, displaySize);
        if (displaySize == 0) {
            resultMsg += "\nOops, no matches found in Dueue."
                    + "You may try with other keywords or find in all tasks.";
        }
        return resultMsg;
    }
//@@author A0147996E
    /**
     * Highlight the single tag if user requests to filter tasks under a single list.
     * Does not support highlighting multiple list names concurrently.
     * @param keywords
     */

    public void highlightCurrentTagName(String keywords) {
        int index = model.getListIndex(keywords.toString());
        if (index != -1) {
            EventsCenter.getInstance().post(new JumpToTagListRequestEvent(index));
        }
    }
//@@author
    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model) {
        this.model = model;
    }

    /**
     * Provides usage message for help command
     */
    public String getUsageMessage() {
        return MESSAGE_USAGE;
    }

    public abstract boolean isUndoable();
}
