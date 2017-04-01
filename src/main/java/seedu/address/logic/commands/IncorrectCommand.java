//@@author generated
package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents an incorrect command. Upon execution, throws a CommandException with feedback to the user.
 */
public class IncorrectCommand extends AbleUndoCommand {

    public final String feedbackToUser;
    public static final String COMMAND_WORD = "incorrect";

    public IncorrectCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(feedbackToUser);
    }

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public CommandResult executeUndo(String message) throws CommandException {
        throw new CommandException(UndoCommand.MESSAGE_UNSUCCESS);
    }

    @Override
    public Command getUndoCommand() throws IllegalValueException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUndoCommandWord() {
        return COMMAND_WORD + COMMAND_SUFFIX;
    }

}

