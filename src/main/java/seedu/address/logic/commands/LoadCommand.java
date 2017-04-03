//@@author A0147996E
package seedu.address.logic.commands;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.StorageManager;

public class LoadCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_LOAD_SUCCESS =
            "Successfully loaded tasks from directory: %1$s";
    public static final String MESSAGE_LOAD_UNSUCCESS =
            "Cannot load tasks from directory";
    public static final String MESSAGE_FILE_NOT_FOUND = "Give directory is empty\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads tasks from an external xml file\n"
            + "Parameters: Directory of the xml file\n"
            + "Example: " + COMMAND_WORD + "  /Users/Alice/Desktop/sampleData.xml";
    public static final String MESSAGE_DIRECTORY_NOT_GIVEN = "Directory not provided\n";

    private String path;

    public LoadCommand(String path) {
        this.path = path;
    }

    @Override
    public CommandResult execute() throws CommandException {
        logger.info(getClass() + " handles current command");
        try {
            StorageManager storage = new StorageManager(path);
            storage.readTaskManager(path);
            return new CommandResult(
                    CommandFormatter.undoFormatter(
                            String.format(MESSAGE_LOAD_SUCCESS, path), COMMAND_WORD));
        } catch (DataConversionException de) {
            throw new CommandException(MESSAGE_LOAD_UNSUCCESS);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_LOAD_UNSUCCESS);
        }
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
