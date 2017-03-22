//@@Author ShermineJong A0138474X
package seedu.address.logic.commands;

import java.util.Stack;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Lists all persons in the address book to the user.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo last task successfully.";
    
    public static final String MESSAGE_UNSUCCESS = "No task to undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List tasks as per the parameters\n"
            + "the specified listname/list index and displays them as a list with index numbers.\n"
            + "Parameters: [LISTNAME/LISTINDEX]\n"
            + "Example: " + COMMAND_WORD + " CS2103";

    private static final String LIST_SEPARATOR = ", ";

    private static final String MESSAGE_UNSUCCESSFUL = null;
    
    private AbleUndoCommand undoCommand;
    private boolean canUndo = false;

    public UndoCommand(Stack<AbleUndoCommand> commandList) {
        do{        
            if (!commandList.isEmpty()) {
                try {
                    this.undoCommand = (AbleUndoCommand) commandList.pop().getUndoCommand();
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
        if(this.undoCommand == null){
            throw new CommandException(MESSAGE_UNSUCCESSFUL);
        }else{
            this.undoCommand.setData(model);
            return this.undoCommand.executeUndo();
        }
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}

