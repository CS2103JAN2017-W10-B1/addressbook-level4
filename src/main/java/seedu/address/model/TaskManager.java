package seedu.address.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.task.Task;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.tasklist.TaskList;
import seedu.address.model.tasklist.UniqueListList;
import seedu.address.model.tasklist.UniqueListList.DuplicateListException;
import seedu.address.model.tasklist.UniqueListList.ListNotFoundExceptionWhenAdding;

/**
 * Wraps all data at the task-manager level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;
    private final UniqueListList lists;

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
        lists = new UniqueListList();
    }

    public TaskManager() {}

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
    
    public void setLists(Collection<TaskList> lists) throws UniqueListList.DuplicateListException {
        this.lists.setLists(lists);
    }

    public void resetData(ReadOnlyTaskManager newData) {
        assert newData != null;
        try {
            setLists(newData.getListList());
        } catch (UniqueListList.DuplicateListException e) {
            assert false : "TaskMangers should not have duplicate lists";
        }
        /*try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "TaskManagers should not have duplicate tags";
        }*/
        try {
            setTasks(newData.getTaskList());
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "TaskManagers should not have duplicate tasks";
        }
        syncMasterTagListWith(tasks);
        
    }

//// task-level operations

    /**
     * Adds a task to the task manager.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     * @throws DuplicateTaskException 
     */
    public void addTask(Task p) throws DuplicateTaskException {
        syncMasterTagListWith(p);
        try {
            updateListsWhenAdding(p);
        } catch (ListNotFoundExceptionWhenAdding e) {
            addNewListWhenAddingTask(p);
        }
        tasks.add(p);
    }

    /** helper method for addTask 
     * @throws DuplicateTaskException */
    private void addNewListWhenAddingTask(Task p) throws DuplicateTaskException {
        TaskList taskList;
        try {
            taskList = new TaskList(p.getTag().getName());
            taskList.add(p);
            addList(taskList);
        } catch (IllegalValueException e) {
            assert false : "not possible";
        }
    }

    /** helper method for addTask 
     * @throws DuplicateTaskException */
    private void updateListsWhenAdding(Task p)
        throws ListNotFoundExceptionWhenAdding, DuplicateTaskException {
        String listName = p.getTag().getName();
        int listIndex;
        try {
            listIndex = lists.indexOf(new TaskList(listName));
            lists.get(listIndex).add(p);
        } catch (IllegalValueException e) {
            assert false : "not possible";
        }
    }

    /**
     * Updates the task in the list at position {@code index} with {@code editedReadOnlyTask}.
     * {@code TaskManager}'s tag list will be updated with the tags of {@code editedReadOnlyTask}.
     * @throws IllegalValueException 
     * @throws DuplicateListException 
     * @throws DuplicateTaskException 
     * @see #syncMasterTagListWith(Task)
     *
     * @throws IndexOutOfBoundsException if {@code index} < 0 or >= the size of the list.
     */
    public void updateTask(int index, ReadOnlyTask editedReadOnlyTask)
            throws DuplicateTaskException {
        assert editedReadOnlyTask != null;

        Task editedTask = new Task(editedReadOnlyTask);
       
        updateListWhenUpdating(index, editedTask);
        
        syncMasterTagListWith(editedTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the task list.
        tasks.updateTask(index, editedTask);
    }

    /** Helper method in update command. Updating the lists */
    private void updateListWhenUpdating(int index, Task editedTask)
            throws DuplicateTaskException {
        removeTaskInOldLisk(index);
        addTaskInNewList(editedTask);
    }

    private void addTaskInNewList(Task editedTask) throws DuplicateTaskException {
        String listNameEdited = editedTask.getTag().getName();
        try {
            int listIndex = lists.indexOf(new TaskList(listNameEdited));
            lists.get(listIndex).add(editedTask);
        } catch (ListNotFoundExceptionWhenAdding e) {
            addNewListWhenAddingTask(editedTask);
        } catch (IllegalValueException e) {
            assert false : "not possible";
        }
    }

    private void removeTaskInOldLisk(int index) {
        ReadOnlyTask originalTask = tasks.get(index);
        String listNameOriginal = originalTask.getTag().getName();
        try {
            int listIndex = lists.indexOf(new TaskList(listNameOriginal));
            lists.get(listIndex).getTasks().remove(originalTask);
        } catch (ListNotFoundExceptionWhenAdding | IllegalValueException e) {
            assert false : "not possible";
        } catch (TaskNotFoundException e) {
            assert false : "not possible";
        }
    }

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
            //TODO: deal after adding lists features
        }
        /*
        // Create map with values = tag object references in the master list
        // used for checking task tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of task tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        task.setTags(new UniqueTagList(correctTagReferences));
        */
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

    
    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        updateListsWhenDeleting(key);
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    /** helper method for deleteTask */
    private void updateListsWhenDeleting(ReadOnlyTask p) {
        String listName = p.getTag().getName();
        int listIndex = -1;
        try {
            listIndex = lists.indexOf(new TaskList(listName));
        } catch (ListNotFoundExceptionWhenAdding | IllegalValueException e) {
            assert false : "not possible";
        }
        assert listIndex >= 0;
        lists.get(listIndex).getTasks().delete(p);
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// list-level operations
    
    /**
     * Add a TaskList in the master lists
     * 
     * @param list
     * @throws UniqueListList.DuplicateListException if lists already has the list with the same name.
     */
    public void addList(TaskList list) throws UniqueListList.DuplicateListException {
        assert list != null;
        
        lists.add(list);
    }
    
    /**
     * Remove a TastList in the master lists
     * 
     * @param list
     * @return true if the removal is successful; false if there is no such list previously
     * @throws UniqueListList.ListNotFoundException if the list is not found
     */
    public boolean removeList(TaskList list) throws UniqueListList.ListNotFoundException {
        if (lists.remove(list)) {
            return true;
        } else {
            throw new UniqueListList.ListNotFoundException();
        }
    }
    
    /**
     * Update a TaskList with new List
     * 
     * @param index
     * @param editedList which is going to replace the old TaskList
     * @throws UniqueListList.DuplicateListException if updating causes duplicate lists in the master list.
     */
    public void updateList(int index, TaskList editedList)
            throws UniqueListList.DuplicateListException {
        assert editedList != null;
        lists.updateList(index, editedList);
    }
    

    
    
//// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + tags.asObservableList().size() +  " tags, " 
                + lists.asObservableList().size() + " lists";
        // TODO: refine later
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
    public ObservableList<TaskList> getListList() {
        return new UnmodifiableObservableList<>(lists.asObservableList());
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.tasks.equals(((TaskManager) other).tasks)
                && this.tags.equalsOrderInsensitive(((TaskManager) other).tags)
                && this.lists.equalsOrderInsensitive(((TaskManager) other).lists));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags, lists);
    }


}
