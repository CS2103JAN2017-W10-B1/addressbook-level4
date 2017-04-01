//@@author A0138474X
package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.TaskManager;

/**
 * Clears the address book.
 */
public class ClearCommand extends AbleUndoCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_CLEAR = "clear command";
    public static final String MESSAGE_SUCCESS = "Dueue has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clear all tasks in Dueue \n"
            + "Parameters: Nil\n"
            + "Example: " + COMMAND_WORD;

    private TaskManager tasks;

    @Override
    public CommandResult execute() {
        assert model != null;
        tasks = new TaskManager();
        tasks.resetData(model.getTaskManager());
        model.resetData(new TaskManager());
        return new CommandResult(CommandFormatter.undoFormatter(MESSAGE_SUCCESS, COMMAND_CLEAR));
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public CommandResult executeUndo(String message) throws CommandException {
        assert model != null;
        TaskManager tasks = new TaskManager();
        tasks.resetData(model.getTaskManager());
        model.resetData(this.tasks);
        this.tasks = tasks;
        return new CommandResult(CommandFormatter.undoMessageFormatter(message, COMMAND_CLEAR));
    }

    @Override
    public Command getUndoCommand() throws IllegalValueException {
        return this;
    }

    @Override
    public String getUndoCommandWord() {
        return COMMAND_WORD;
    }

}
