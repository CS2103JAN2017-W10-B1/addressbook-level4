//@@Author ShermineJong A0138474X
package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskManager;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.tasklist.TaskList;


/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableTaskManager implements ReadOnlyTaskManager {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTaskManager() {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTaskManager(ReadOnlyTaskManager src) {
        this();
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        final ObservableList<Task> task = this.tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(task);
    }

    @Override
    public ObservableList<Tag> getTagList() {
        final ObservableList<Tag> tags = this.tags.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(tags);
    }

    public ObservableList<TaskList> getList() {
        ArrayList<TaskList> list = new ArrayList<>();
        try{
            for(XmlAdaptedTag tag : this.tags){
                TaskList taskList = new TaskList(tag.tagName);
                for(XmlAdaptedTask t: this.tasks){
                    if(t.getTagName().equals(tag.tagName)){
                        taskList.add(t.toModelType());
                    }
                }
                list.add(taskList);
            }
        }catch (IllegalValueException e) {
            e.printStackTrace();
            //TODO: better error handling
            return null;
        }
        final ObservableList<TaskList> lists = list.stream().collect(Collectors.toCollection(FXCollections::observableArrayList));;
        return new UnmodifiableObservableList<>(lists);
    }
}
