package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

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

    /** Returns the filtered list list as an {@code UnmodifiableObservableList<TaskList>} */
    UnmodifiableObservableList<Tag> getFilteredTagList();

    /** Updates the filter of the filtered task list to show all unfinished tasks */
    void updateFilteredListToShowAllUnfinishedTasks();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAllTasks();

    /** Updates the filter of the filtered task list to show all finished tasks */
    void updateFilteredListToShowAllFinishedTasks();

    /** Updates the filter of the filtered task list to show all favorite tasks */
    void updateFilteredListToShowAllFavoriteTasks();

    /** Updates the filter of the unfinished filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskListAll(Set<String> keywords);

    /** Updates the filter of the unfinished filtered task list to filter by the given list name*/
    void updateFilteredTaskListGivenListName(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the given list name*/
    void updateFilteredTaskListGivenListNameAll(Set<String> keywords);

    /** Updates the filter of the filtered task list to filter by the days to due*/
    void updateFilteredTaskListGivenDaysToDueBy(String days);

    /** Updates the filter of the filtered task list to filter on the days to due*/
    void updateFilteredTaskListGivenDaysToDueOn(String days);

    /** Adds the given list */
    void addList(Tag tag) throws UniqueTagList.DuplicateTagException;

    /** Updates the filter of the filtered tag list to show all tasks */
    void updateFilteredTagListToShowAllTags();

    /** Updates the filter of the filtered tag list to filter by the given keywords*/
    void updateFilteredListList(Set<String> keywords);

    /** Check whether the listname has already been taken*/
    boolean isListExist(Set<String> listNames);

}
