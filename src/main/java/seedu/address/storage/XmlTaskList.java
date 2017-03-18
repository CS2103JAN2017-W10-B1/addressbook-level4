package seedu.address.storage;


import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;
import seedu.address.model.tasklist.TaskList;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlTaskList {

    @XmlValue
    public String taskListName;
    @XmlValue
    @XmlElement
    private List<XmlAdaptedTask> tasksList;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlTaskList() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlTaskList(TaskList source) {
        taskListName = source.getName();
        tasksList.addAll(source.getTasks().asObservableList().stream().map(
                XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public TaskList toModelType() throws IllegalValueException {
        TaskList taskList = new TaskList(taskListName);
        final ObservableList<Task> tasks = this.tasksList.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        tasksList.addAll(tasks.stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        return taskList;
    }

}
