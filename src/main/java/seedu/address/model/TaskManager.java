package seedu.address.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.Event;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.ReadOnlyTask.RecurringProperty;
import seedu.address.model.task.RecurringEvent;
import seedu.address.model.task.RecurringTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Wraps all data at the task-manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {
    private static final Logger logger = LogsCenter.getLogger(TaskManager.class);

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;
    private static TaskManager instance;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

//@@author A0147996E
    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }
    public static TaskManager getStub() {
        return new TaskManager();
    }
//@@author

    private TaskManager() {}

    /**
     * Creates an TaskManager using the Tasks ,Tags and Lists in the {@code toBeCopied}
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

//// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks)
            throws UniqueTaskList.DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        assert newData != null;
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "TaskManagers should not have duplicate tasks";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "TaskManagers should not have duplicate tags";
        }
        syncMasterTagListWith(tasks);
    }

    //@@author A0147984L
    /**
     * Reset the task manager
     */
    public void clear() {
        this.tasks.clear();
        this.tags.clear();
    }
    //@@author

    // task-level operations

    /**
     * Adds a task to the task manager.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     * @throws DuplicateTaskException
     */
    public void addTask(Task p) throws DuplicateTaskException {
        syncMasterTagListWith(p);
        tasks.add(p);
        tasks.sort();
        tags.sort();
    }

    //@@author A0147984L
    /**
     * Updates the task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * {@code TaskManager}'s tag list will be updated with the tags of {@code editedReadOnlyTask}.
     * @throws DuplicateTaskException
     * @see #syncMasterTagListWith(Task)
     *
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask)
            throws DuplicateTaskException {
        assert editedReadOnlyTask != null;
        Task editedTask = buildEditedTask(editedReadOnlyTask);

        syncMasterTagListWith(editedTask);
        updateTasksAndTagsWhenUpdating(index, editedTask);
    }

    private void updateTasksAndTagsWhenUpdating(int index, Task editedTask) throws DuplicateTaskException {
        Tag oldTaskTag = tasks.get(index).getTag();
        tasks.updateTask(index, editedTask);
        tasks.sort();
        if (isEmptyTag(oldTaskTag)) {
            tags.remove(oldTaskTag);
        }
        if (isEmptyTag(editedTask.getTag())) {
            tags.remove(editedTask.getTag());
        }
        tags.sort();
    }

    private Task buildEditedTask(ReadOnlyTask editedReadOnlyTask) {
        if (editedReadOnlyTask.isEvent()) {
            try {
                return new Event(editedReadOnlyTask);
            } catch (IllegalValueException e) {
                logger.info("IllegalValueException thrown when building"
                        + " event in updating task manager");
            }
        } else if (editedReadOnlyTask.isRecurring()) {
            return new RecurringTask(editedReadOnlyTask);
        } else {
            return new Task(editedReadOnlyTask);
        }
        return null;
    }

    /**
     * Updates the recurring task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * The recurring task will be postpone for one period,
     * add a new Task will be created
     * @throws DuplicateTaskException
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTaskOnce(int index, ReadOnlyTask editedReadOnlyTask)
            throws UniqueTaskList.DuplicateTaskException {
        assert editedReadOnlyTask != null;

        ReadOnlyTask recurringTask = tasks.get(index);
        finishTaskOnce(recurringTask);

        Task editedTask = buildEditedTaskOneTime(editedReadOnlyTask);
        addTask(editedTask);
    }

    private Task buildEditedTaskOneTime(ReadOnlyTask editedReadOnlyTask) {
        Task editedTask = null;
        if (editedReadOnlyTask.isEvent()) {
            try {
                editedTask = new Event(editedReadOnlyTask);
            } catch (IllegalValueException e) {
                logger.info("IllegalValueException thrown when building"
                        + " event in updating task manager");
            }
        } else {
            editedTask = new Task(editedReadOnlyTask);
            editedTask.setRecurringProperty(RecurringProperty.NON_RECURRING);
        }
        return editedTask;
    }

    /**
     * Finish the recurring task once
     * @param recurringTask
     * @throws DuplicateTaskException
     */
    public void finishTaskOnce(ReadOnlyTask recurringTask) throws DuplicateTaskException {
        Task current = buildFinishedRecurringTask(recurringTask);
        if (current.isEvent()) {
            ((RecurringEvent) current).finishOnce();
        } else {
            ((RecurringTask) current).finishOnce();
        }
        replaceTask(recurringTask, current);
    }

    private void replaceTask(ReadOnlyTask recurringTask, Task current) throws DuplicateTaskException {
        try {
            removeTask(recurringTask);
            addTask(current);
        } catch (TaskNotFoundException e) {
            logger.info("task is not found");
        }
    }

    private Task buildFinishedRecurringTask(ReadOnlyTask recurringTask) {
        Task current = null;
        try {
            if (recurringTask.isEvent()) {
                current = new RecurringEvent(recurringTask);
            } else {
                current = new RecurringTask(recurringTask);
            }
        } catch (IllegalValueException e1) {
            logger.info("IllegalValueException thrown when building"
                    + " recurring task in updating task manager");
        }
        return current;
    }
    //@@author

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Task task) {
        final Tag taskTag = task.getTag();
        try {
            tags.add(taskTag);
        } catch (DuplicateTagException e) {
            logger.info("task manager already has the tag for new task");
        }
    }

    /**
     * Ensures that every tag in these tasks:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Task)
     */
    private void syncMasterTagListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
    }

    /**
     * Remove the task in the task manager
     * @param key
     * @return
     * @throws UniqueTaskList.TaskNotFoundException
     */
    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            if (isEmptyTag(key.getTag())) {
                tags.remove(key.getTag());
                tags.sort();
            }
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

//// tag-level operations
    //@@author A0147984L
    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
        logger.info(t + " is addded in task manager");
    }

    public void removeTag(Tag t) {
        tags.remove(t);
        logger.info(t + " is deleted in task manager");
    }


    private boolean isEmptyTag(Tag keyTag) {
        for (Task t : tasks) {
            if (t.getTag().equals(keyTag)) {
                return false;
            }
        }
        return true;
    }
    //@@author

//// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + tags.asObservableList().size() +  " tags, ";
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.tasks.equals(((TaskManager) other).tasks)
                && this.tags.equalsOrderInsensitive(((TaskManager) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
