//@@author A0147996E
package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.TaskManager;
import seedu.address.storage.StorageManager;

/**
 * Load tasks from an external XML file into Dueue.
 */
public class LoadCommand extends AbleUndoCommand {

    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_ILLEGAL_DATA_TYPE = "File must be in XML format";
    public static final String MESSAGE_LOAD_SUCCESS = "Successfully loaded tasks from directory: %1$s";
    public static final String MESSAGE_LOAD_UNSUCCESS = "Cannot load tasks from directory";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads tasks from an external xml file\n"
            + "Parameters: Directory of the xml file\n"
            + "Example: " + COMMAND_WORD + " /Users/Alice/Desktop/sampleData.xml";
    public static final String MESSAGE_DIRECTORY_NOT_GIVEN = "Directory not provided\n";
    public static final String PATH_VALIDATION_REGEX = ".*xml";

    private String path;
    private TaskManager tempTaskManager;

    public LoadCommand(String path) {
        this.path = path;
    }

    @Override
    public CommandResult execute() throws CommandException {
        saveTaskManagerCopy();
        if (!path.matches(PATH_VALIDATION_REGEX)) {
            LOGGER.info(getClass() + "Input file " + path + "is not in XML format. Load unsuccessful");
            throw new CommandException(MESSAGE_ILLEGAL_DATA_TYPE);
        }

        if (resetDataFromFilePath(path)) {
            return new CommandResult(CommandFormatter.undoFormatter(
                    String.format(MESSAGE_LOAD_SUCCESS, path), COMMAND_WORD));
        } else {
            return new CommandResult(MESSAGE_LOAD_UNSUCCESS);
        }
    }

    /**
     * Create a copy of current TaskManager in case user requests to undo the load command.
     * @param tempTaskManager A temporary task manager copy saved for undo command.
     */
    private void saveTaskManagerCopy() {
        LOGGER.info(getClass() + "Start saving a copy of current task manager");
        assert model != null;
        tempTaskManager = TaskManager.getStub();
        tempTaskManager.resetData(model.getTaskManager());
    }

    /**
     * Try to reset data within the single instance of model.
     *
     * @param path User input directory used to load tasks from.
     * @return If data resetting is successful.
     * @throws CommandException Catches exceptions when storage attempts to read file from the directory.
     */
    private boolean resetDataFromFilePath(String path) throws CommandException {
        try {
            LOGGER.info(getClass() + "Start resetting data in current model from given directory");
            StorageManager storage = new StorageManager(path);
            ReadOnlyTaskManager taskManager = storage.readTaskManager(path).get();
            model.resetData(taskManager);
            return true;
        } catch (DataConversionException de) {
            throw new CommandException(MESSAGE_ILLEGAL_DATA_TYPE);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_LOAD_UNSUCCESS);
        }
    }

    @Override
    public CommandResult undo(String message) throws CommandException {
        assert model != null;
        TaskManager newTaskManager = TaskManager.getStub();
        newTaskManager.resetData(model.getTaskManager());
        model.resetData(this.tempTaskManager);
        this.tempTaskManager = newTaskManager;
        return new CommandResult(CommandFormatter.undoMessageFormatter(message, getUndoCommandWord()));
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

    @Override
    public String getRedoCommandWord() {
        return COMMAND_WORD + COMMAND_SUFFIX;
    }
}
