package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class AbleUndoCommand extends Command {

    public static final String UNDO_COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_TASK_SUCCESS = "Undo last command successful";

    public abstract CommandResult executeUndo(String message) throws CommandException;

    public abstract Command getUndoCommand() throws IllegalValueException;

}

