//@@author A0147996E
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Scrolls to a task identified using it's last displayed index from the Dueue.
 * @param targetIndex Index given by user, not the internal index used by task list
 */
public class ScrollToCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "scroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Scrolls to the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SCROLL_TASK_SUCCESS = "Scrolled to index %1$s";

    public ScrollToCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SCROLL_TASK_SUCCESS, targetIndex));
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
