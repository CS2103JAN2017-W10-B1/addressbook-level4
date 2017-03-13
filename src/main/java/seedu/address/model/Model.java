package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.tasklist.TaskList;
import seedu.address.model.tasklist.UniqueListList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicateTaskException;

    /**
     * Updates the task located at {@code filteredTaskListIndex} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws IndexOutOfBoundsException if {@code filteredTaskListIndex} < 0 or >= the size of the filtered list.
     */
    void updateTask(int filteredTaskListIndex, ReadOnlyTask editedTask)
            throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAllTasks();
    
    /** Updates the filter of the filtered task list to show all tasks (includes finished)*/
    void updateFilteredListToShowAllTasksAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given keywords (includes finished)*/
    void updateFilteredTaskListAll(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given list name*/
    void updateFilteredTaskListGivenListName(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the given list name (includes finished)*/
    void updateFilteredTaskListGivenListNameAll(Set<String> keywords);
    
    /** Adds the given TaskList */
    void addList(TaskList list) throws UniqueListList.DuplicateListException;
    
    /** Removes the given TaskList */
    void removeList(TaskList list) throws UniqueListList.ListNotFoundException;
    
    /** Updates the given TaskList with new TaskList*/
    void updateList(int filteredListListIndex, TaskList editedList) throws UniqueListList.DuplicateListException;
    
    /** Returns the filtered list list as an {@code UnmodifiableObservableList<TaskList>} */
    UnmodifiableObservableList<TaskList> getFilteredListList();

    /** Updates the filter of the filtered list list to show all tasks */
    void updateFilteredListToShowAllLists();

    /** Updates the filter of the filtered list list to filter by the given keywords*/
    void updateFilteredListList(Set<String> keywords);
}
