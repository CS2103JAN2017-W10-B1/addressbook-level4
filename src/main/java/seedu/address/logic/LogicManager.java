//@@author A0138474X
package seedu.address.logic;

import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AbleUndoCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
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
    private final Stack<AbleUndoCommand> commandList;
    private final Stack<AbleUndoCommand> redoCommandList;

    public LogicManager(Model model, Storage storage) throws CommandException {
        this.model = model;
        this.parser = new Parser();
        this.commandList = new Stack<AbleUndoCommand>();
        this.redoCommandList = new Stack<AbleUndoCommand>();
        //execute("list");
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText, commandList, redoCommandList);
        command.setData(model);
        if (command.isUndoable()) {
            commandList.push((AbleUndoCommand) command);
        }
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return model.getFilteredTagList();
    }
}
