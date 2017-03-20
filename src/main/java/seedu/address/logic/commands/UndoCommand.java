package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class UndoCommand extends Command {

        public static final String UNDO_COMMAND_WORD = "undo";

        public static final String MESSAGE_UNDO_TASK_SUCCESS = "Undo last command";
        
        public static final String MESSAGE_UNDO_TASK_NOT_SUCCESS = "No command to undo";


        public abstract CommandResult executeUndo() throws CommandException;
        
        public abstract Command getUndoCommand() throws IllegalValueException;

}

