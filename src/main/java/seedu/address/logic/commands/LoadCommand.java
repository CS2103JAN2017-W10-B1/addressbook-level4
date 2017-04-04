//@@author A0147996E
package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.storage.StorageManager;

public class LoadCommand extends AbleUndoCommand {

    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_ILLEGAL_DATA_TYPE = "File must be in XML format";
    public static final String MESSAGE_LOAD_SUCCESS = "Successfully loaded tasks from directory: %1$s";
    public static final String MESSAGE_LOAD_UNSUCCESS = "Cannot load tasks from directory";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads tasks from an external xml file\n"
            + "Parameters: Directory of the xml file\n"
            + "Example: " + COMMAND_WORD + "  /Users/Alice/Desktop/sampleData.xml";
    public static final String MESSAGE_DIRECTORY_NOT_GIVEN = "Directory not provided\n";
    public static final String PATH_VALIDATION_REGEX = ".*xml";

    private String path;
    private TaskManager tempTaskManager;

    public LoadCommand(String path) {
        this.path = path;
    }

    /**
     * @tempTaskManager a temporary task manager copy saved for undo command.
     */
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        tempTaskManager = TaskManager.getStub();
        tempTaskManager.resetData(model.getTaskManager());
<<<<<<< HEAD
        if (!path.matches(PATH_VALIDATION_REGEX)) {
            throw new CommandException(MESSAGE_ILLEGAL_DATA_TYPE);
        }
        LOGGER.info(getClass() + " handles current command");
=======

>>>>>>> origin/master
        try {
            File file = new File(path);
            assert file != null;
            StorageManager storage = new StorageManager(path);
            ReadOnlyTaskManager taskManager = storage.readTaskManager(path).get();
            model.resetData(taskManager);
            return new CommandResult(
                    CommandFormatter.undoFormatter(
                            String.format(MESSAGE_LOAD_SUCCESS, path), COMMAND_WORD));
        } catch (DataConversionException de) {
            throw new CommandException(MESSAGE_ILLEGAL_DATA_TYPE);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_LOAD_UNSUCCESS);
        }
    }

    @Override
    public CommandResult executeUndo(String message) throws CommandException {
        assert model != null;
        TaskManager newTaskManager = TaskManager.getStub();
        newTaskManager.resetData(model.getTaskManager());
        model.resetData(this.tempTaskManager);
        this.tempTaskManager = newTaskManager;
        return new CommandResult(CommandFormatter.undoMessageFormatter(message, COMMAND_WORD));
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public Command getUndoCommand() throws IllegalValueException {
        return this;
    }

    @Override
    public String getUndoCommandWord() {
        return COMMAND_WORD + COMMAND_SUFFIX;
    }
}
