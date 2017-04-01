//@@author A0138474X
package seedu.address.logic.commands;

import java.util.Stack;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Lists all persons in the address book to the user.
 */
public class RedoCommand extends AbleUndoCommand {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redo last task successfully.";

    public static final String MESSAGE_UNSUCCESS = "No task to redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks as per the parameters\n"
            + "the specified listname/list index and displays them as a list with index numbers.\n"
            + "Parameters: [LISTNAME/LISTINDEX]\n"
            + "Example: " + COMMAND_WORD + " CS2103";

    private AbleUndoCommand undoCommand;
    private boolean canUndo = false;

    public RedoCommand(Stack<AbleUndoCommand> undoCommandList) {
        do {
            if (!undoCommandList.isEmpty()) {
                try {
                    this.undoCommand = (AbleUndoCommand) undoCommandList.pop().getUndoCommand();
                } catch (IllegalValueException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (!this.undoCommand.COMMAND_WORD.equals(IncorrectCommand.COMMAND_WORD)) {
                    this.canUndo = false;
                } else {
                    this.canUndo = true;
                }
            }
        } while(canUndo);

    }

    @Override
    public CommandResult execute() throws CommandException {
        if (this.undoCommand == null) {
            throw new CommandException(MESSAGE_UNSUCCESS);
        } else {
            this.undoCommand.setData(model);
            return this.undoCommand.executeUndo(MESSAGE_SUCCESS);
        }
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public CommandResult executeUndo(String message) throws CommandException {
        return null;
    }

    @Override
    public Command getUndoCommand() throws IllegalValueException {
        return this.undoCommand.getUndoCommand();
    }

    @Override
    public String getUndoCommandWord() {
        return COMMAND_WORD;
    }

}

