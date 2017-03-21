package seedu.address.logic;

import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private final Stack<UndoCommand> commandList;
    private boolean canUndo;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
        this.commandList = new Stack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        if (commandText.equals(UndoCommand.UNDO_COMMAND_WORD)) {
            do{
                if (!commandList.isEmpty()) {
                    UndoCommand command = null;
                    try {
                        command = (UndoCommand) commandList.pop().getUndoCommand();
                    } catch (IllegalValueException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if(!command.COMMAND_WORD.equals(IncorrectCommand.COMMAND_WORD)){
                        command.setData(model);
                        return command.executeUndo();
                    }
                    else{
                        this.canUndo = true;
                    }
                }
            }while(canUndo);
            return new CommandResult(UndoCommand.MESSAGE_UNDO_TASK_NOT_SUCCESS);
        } else {
            Command command = parser.parseCommand(commandText);
            command.setData(model);
            if (command.isUndoable()) {
                commandList.push((UndoCommand) command);
            }
            return command.execute();
        }
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<Tag> getFilteredListList() {
        return model.getFilteredListList();
    }
}
