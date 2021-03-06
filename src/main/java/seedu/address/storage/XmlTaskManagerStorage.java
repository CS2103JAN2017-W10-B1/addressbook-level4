//@@author A0138474X
package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskManager;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlTaskManagerStorage implements TaskManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskManagerStorage.class);

    private String filePath;
    private static XmlTaskManagerStorage xmlStorage;

    protected XmlTaskManagerStorage(String filePath) {
        this.filePath = filePath;
    }

    public static XmlTaskManagerStorage getInstance(String filePath) {
        if (xmlStorage == null) {
            return xmlStorage = new XmlTaskManagerStorage(filePath);
        } else {
            xmlStorage.changeFilePath(filePath);
            return xmlStorage;
        }
    }

    public String getTaskManagerFilePath() {
        return filePath;
    }

    public void changeFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     * @throws FileNotFoundException
     */
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws
        DataConversionException, FileNotFoundException {
        assert filePath != null;
        File taskManagerFile = new File(filePath);

        if (!taskManagerFile.exists()) {
            logger.info("TaskManager file "  + taskManagerFile + " not found");
        }
        ReadOnlyTaskManager taskManagerOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskManagerOptional);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        assert taskManager != null;
        assert filePath != null;

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(taskManager));
    }

}
