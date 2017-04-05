//@@author A0138474X
package seedu.address.logic.commands;

import java.util.Stack;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Undo the previous undoable command in Dueue.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo last task successfully.\n"
            + " You can type 'redo' to revert this undo.\n";

    public static final String MESSAGE_UNSUCCESS = "No task to undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks as per the parameters\n"
            + "the specified listname/list index and displays them as a list with index numbers.\n"
            + "Parameters: [LISTNAME/LISTINDEX]\n"
            + "Example: " + COMMAND_WORD + " CS2103";

    private AbleUndoCommand undoCommand;
    private boolean canUndo = false;
    private Stack<AbleUndoCommand> undoCommandList;

    public UndoCommand(Stack<AbleUndoCommand> commandList, Stack<AbleUndoCommand> undoCommandList) {
        do {
            if (!commandList.isEmpty()) {
                try {
                    if (this.undoCommand.COMMAND_WORD.equals(RedoCommand.COMMAND_WORD)) {
                        undoCommandList.clear();
                    }
                    this.undoCommand = (AbleUndoCommand) commandList.pop().getUndoCommand();
                    this.undoCommandList = undoCommandList;
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
            this.undoCommandList.push(this.undoCommand);
            return this.undoCommand.executeUndo(
                    CommandFormatter.undoMessageFormatter(MESSAGE_SUCCESS, this.undoCommand));
        }
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
